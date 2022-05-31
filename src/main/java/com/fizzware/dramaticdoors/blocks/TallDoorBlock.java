package com.fizzware.dramaticdoors.blocks;

import java.util.Random;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.commons.lang3.NotImplementedException;

import com.fizzware.dramaticdoors.DDTags;
import com.fizzware.dramaticdoors.blocks.DDBlocks.DoorSeries;
import com.fizzware.dramaticdoors.compat.Compats;
import com.fizzware.dramaticdoors.state.properties.DDBlockStateProperties;
import com.fizzware.dramaticdoors.state.properties.TripleBlockPart;
import com.mojang.blaze3d.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public class TallDoorBlock extends Block {

	//Vanilla
    public static final String NAME_OAK = "tall_oak_door";
    public static final String NAME_SPRUCE = "tall_spruce_door";
    public static final String NAME_BIRCH = "tall_birch_door";
    public static final String NAME_JUNGLE = "tall_jungle_door";
    public static final String NAME_ACACIA = "tall_acacia_door";
    public static final String NAME_DARK_OAK = "tall_dark_oak_door";
    public static final String NAME_IRON = "tall_iron_door";
    public static final String NAME_CRIMSON = "tall_crimson_door";
    public static final String NAME_WARPED = "tall_warped_door";
    //public static final String NAME_MANGROVE = "tall_mangrove_door"; To be added in 1.19+

    //Biomes o' Plenty (Given name prefix to avoid conflicts)
    public static final String NAME_BOP_CHERRY = "tall_bop_cherry_door";
    public static final String NAME_BOP_DEAD = "tall_bop_dead_door";
    public static final String NAME_BOP_FIR = "tall_bop_fir_door";
    public static final String NAME_BOP_HELLBARK = "tall_bop_hellbark_door";
    public static final String NAME_BOP_JACARANDA = "tall_bop_jacaranda_door";
    public static final String NAME_BOP_MAGIC = "tall_bop_magic_door";
    public static final String NAME_BOP_MAHOGANY = "tall_bop_mahogany_door";
    public static final String NAME_BOP_PALM = "tall_bop_palm_door";
    public static final String NAME_BOP_REDWOOD = "tall_bop_redwood_door";
    public static final String NAME_BOP_UMBRAN = "tall_bop_umbran_door";
    public static final String NAME_BOP_WILLOW = "tall_bop_willow_door";
    
    //Oh the Biomes You'll Go (Also given name prefix to avoid conflicts too)
    public static final String NAME_BYG_ASPEN = "tall_byg_aspen_door";
    public static final String NAME_BYG_BAOBAB = "tall_byg_baobab_door";
    public static final String NAME_BYG_BLUE_ENCHANTED = "tall_byg_blue_enchanted_door";
    public static final String NAME_BYG_BULBIS = "tall_byg_bulbis_door";
    public static final String NAME_BYG_CHERRY = "tall_byg_cherry_door";
    public static final String NAME_BYG_CIKA = "tall_byg_cika_door";
    public static final String NAME_BYG_CYPRESS = "tall_byg_cypress_door";
    public static final String NAME_BYG_EBONY = "tall_byg_ebony_door";
    public static final String NAME_BYG_EMBUR = "tall_byg_embur_door";
    public static final String NAME_BYG_ETHER = "tall_byg_ether_door";
    public static final String NAME_BYG_FIR = "tall_byg_fir_door";
    public static final String NAME_BYG_FLORUS = "tall_byg_florus_door";
    public static final String NAME_BYG_GREEN_ENCHANTED = "tall_byg_green_enchanted_door";
    public static final String NAME_BYG_HOLLY = "tall_byg_holly_door";
    public static final String NAME_BYG_IMPARIUS = "tall_byg_imparius_door";
    public static final String NAME_BYG_IRONWOOD = "tall_byg_ironwood_door";
    public static final String NAME_BYG_JACARANDA = "tall_byg_jacaranda_door";
    public static final String NAME_BYG_LAMENT = "tall_byg_lament_door";
    public static final String NAME_BYG_MAHOGANY = "tall_byg_mahogany_door";
    public static final String NAME_BYG_MANGROVE = "tall_byg_mangrove_door";
    public static final String NAME_BYG_MAPLE = "tall_byg_maple_door";
    public static final String NAME_BYG_NIGHTSHADE = "tall_byg_nightshade_door";
    public static final String NAME_BYG_PALM = "tall_byg_palm_door";
    public static final String NAME_BYG_PINE = "tall_byg_pine_door";
    public static final String NAME_BYG_RAINBOW_EUCALYPTUS = "tall_byg_rainbow_eucalyptus_door";
    public static final String NAME_BYG_REDWOOD = "tall_byg_redwood_door";
    public static final String NAME_BYG_SKYRIS = "tall_byg_skyris_door";
    public static final String NAME_BYG_SOUL_SHROOM = "tall_byg_soul_shroom_door";
    public static final String NAME_BYG_SYTHIAN = "tall_byg_sythian_door";
    public static final String NAME_BYG_WILLOW = "tall_byg_willow_door";
    public static final String NAME_BYG_WITCH_HAZEL = "tall_byg_witch_hazel_door";
    public static final String NAME_BYG_ZELKOVA = "tall_byg_zelkova_door";
    
    //Prehistoric Fauna
    public static final String NAME_ARAUCARIA = "tall_araucaria_door";
    public static final String NAME_HEIDIPHYLLUM = "tall_heidiphyllum_door";
    public static final String NAME_LIRIODENDRITES = "tall_liriodendrites_door";
    public static final String NAME_METASEQUOIA = "tall_metasequoia_door";
    public static final String NAME_PROTOJUNIPEROXYLON = "tall_protojuniperoxylon_door";
    public static final String NAME_PROTOPICEOXYLON = "tall_protopiceoxylon_door";
    public static final String NAME_ZAMITES = "tall_zamites_door";
    
    //Twilight Forest
    public static final String NAME_CANOPY = "tall_canopy_door";
    public static final String NAME_DARKWOOD = "tall_darkwood_door";
    public static final String NAME_TWILIGHT_MANGROVE = "tall_twilight_mangrove_door";
    public static final String NAME_MINEWOOD = "tall_minewood_door";
    public static final String NAME_SORTINGWOOD = "tall_sortingwood_door";
    public static final String NAME_TIMEWOOD = "tall_timewood_door";
    public static final String NAME_TRANSWOOD = "tall_transwood_door";
    public static final String NAME_TWILIGHT_OAK = "tall_twilight_oak_door";
    
    //Atmospheric
    public static final String NAME_ASPEN = "tall_aspen_door";
    public static final String NAME_GRIMWOOD = "tall_grimwood_door";
    public static final String NAME_KOUSA = "tall_kousa_door";
    public static final String NAME_MORADO = "tall_morado_door";
    public static final String NAME_ROSEWOOD = "tall_rosewood_door";
    public static final String NAME_YUCCA = "tall_yucca_door";
    
    //Autumnity
    public static final String NAME_MAPLE = "tall_maple_door";
    
    //Bamboo Blocks
    public static final String NAME_BAMBOO = "tall_bamboo_door";
    
    //Buzzier Bees
    public static final String NAME_HONEYCOMB = "tall_honeycomb_door";
    
    //Caverns & Chasms
    public static final String NAME_AZALEA = "tall_azalea_door";

    //Endergetic Expansion
    public static final String NAME_POISE = "tall_poise_door";
    
    //Environmental
    public static final String NAME_CHERRY = "tall_cherry_door";
    public static final String NAME_WILLOW = "tall_willow_door";
    public static final String NAME_WISTERIA = "tall_wisteria_door";

    //Upgrade Aquatic
    public static final String NAME_DRIFTWOOD = "tall_driftwood_door";
    public static final String NAME_RIVER = "tall_river_door";
    public static final String NAME_GLASS = "tall_glass_door";
    public static final String NAME_TOOTH = "tall_tooth_door";
    
    //Abundance
    public static final String NAME_JACARANDA = "tall_jacaranda_door";
    public static final String NAME_REDBUD = "tall_redbud_door";
    
    //Bayou Blues
    public static final String NAME_CYPRESS = "tall_cypress_door";
    
    //Enhanced Mushrooms
    public static final String NAME_BROWN_MUSHROOM = "tall_brown_mushroom_door";
    public static final String NAME_RED_MUSHROOM = "tall_red_mushroom_door";
    public static final String NAME_GLOWSHROOM = "tall_glowshroom_door";

    //Architects Palette
    public static final String NAME_TWISTED = "tall_twisted_door";
    
    //Blocks+
    public static final String NAME_BP_BAMBOO = "tall_bp_bamboo_door";
    public static final String NAME_BP_MUSHROOM = "tall_bp_mushroom_door";
    public static final String NAME_BP_COPPER = "tall_bp_copper_door";
    public static final String NAME_BP_DIAMOND = "tall_bp_diamond_door";
    public static final String NAME_BP_EMERALD = "tall_bp_emerald_door";
    public static final String NAME_BP_GOLDEN = "tall_bp_golden_door";
    public static final String NAME_BP_NETHERITE = "tall_bp_netherite_door";
    
    //Create: Alloyed
    public static final String NAME_STEEL = "tall_steel_door";
    public static final String NAME_LOCKED_STEEL = "tall_locked_steel_door";
    
    //Create: Deco
    public static final String NAME_ANDESITE = "tall_andesite_door";
    public static final String NAME_BRASS = "tall_brass_door";
    public static final String NAME_COPPER = "tall_copper_door";
    public static final String NAME_ZINC = "tall_zinc_door";
    public static final String NAME_LOCKED_ANDESITE = "tall_locked_andesite_door";
    public static final String NAME_LOCKED_BRASS = "tall_locked_brass_door";
    public static final String NAME_LOCKED_COPPER = "tall_locked_copper_door";
    public static final String NAME_LOCKED_ZINC = "tall_locked_zinc_door";
    
    //Darker Depths
    public static final String NAME_PETRIFIED = "tall_petrified_door";
        
    //Dustrial Decor
    public static final String NAME_CARDBOARD = "tall_cardboard_door";
    public static final String NAME_CHAIN = "tall_chain_door";
    public static final String NAME_INDUSTRIAL_IRON = "tall_industrial_iron_door";
    public static final String NAME_IRON_BAR = "tall_iron_bar_door";
    public static final String NAME_PADDED = "tall_padded_door";
    public static final String NAME_RUSTY_IRON = "tall_rusty_iron_door";
    public static final String NAME_RUSTY_SHEET_METAL = "tall_rusty_sheet_metal_door";
    public static final String NAME_SHEET_METAL = "tall_sheet_metal_door";

    //Ecologics
    public static final String NAME_ECO_AZALEA = "tall_eco_azalea_door";
    public static final String NAME_ECO_FLOWERING_AZALEA = "tall_eco_flowering_azalea_door";
    public static final String NAME_ECO_COCONUT = "tall_eco_coconut_door";
    public static final String NAME_ECO_WALNUT = "tall_eco_walnut_door";
    
    //Habitat
    public static final String NAME_FAIRY_RING_MUSHROOM = "tall_fairy_ring_mushroom_door";
    
    //Outer End
    public static final String NAME_AZURE = "tall_azure_door";
    
    //Pokecube
    public static final String NAME_POKECUBE_ENIGMA = "tall_pokecube_enigma_door";
    public static final String NAME_POKECUBE_LEPPA = "tall_pokecube_leppa_door";
    public static final String NAME_POKECUBE_NANAB = "tall_pokecube_nanab_door";
    public static final String NAME_POKECUBE_ORAN = "tall_pokecube_oran_door";
    public static final String NAME_POKECUBE_PECHA = "tall_pokecube_pecha_door";
    public static final String NAME_POKECUBE_SITRUS = "tall_pokecube_sitrus_door";
    public static final String NAME_POKECUBE_AGED = "tall_pokecube_aged_door";
    public static final String NAME_POKECUBE_CONCRETE = "tall_pokecube_concrete_door";
    public static final String NAME_POKECUBE_CORRUPTED = "tall_pokecube_corrupted_door";
    public static final String NAME_POKECUBE_DISTORTIC = "tall_pokecube_distortic_door";
    public static final String NAME_POKECUBE_INVERTED = "tall_pokecube_inverted_door";
    public static final String NAME_POKECUBE_MIRAGE = "tall_pokecube_mirage_door";
    public static final String NAME_POKECUBE_TEMPORAL = "tall_pokecube_temporal_door";
    
    //Premium Wood
    public static final String NAME_PW_MAGIC = "tall_pw_magic_door";
    public static final String NAME_PW_MAPLE = "tall_pw_maple_door";
    public static final String NAME_PW_PURPLE_HEART = "tall_pw_purple_heart_door";
    public static final String NAME_PW_SILVERBELL = "tall_pw_silverbell_door";
    public static final String NAME_PW_TIGER = "tall_pw_tiger_door";
    public static final String NAME_PW_WILLOW = "tall_pw_willow_door";
    
    //Quark
    public static final String NAME_QUARK_AZALEA = "tall_quark_azalea_door";
    public static final String NAME_QUARK_BLOSSOM = "tall_quark_blossom_door";
    
    //Supplementaries
    public static final String NAME_GOLD = "tall_gold_door";
    public static final String NAME_SILVER = "tall_silver_door";
    public static final String NAME_LEAD = "tall_lead_door";
    public static final String NAME_NETHERITE = "tall_netherite_door";
    
    //Undergarden
    public static final String NAME_GRONGLE = "tall_grongle_door";
    public static final String NAME_SMOGSTEM = "tall_smogstem_door";
    public static final String NAME_WIGGLEWOOD = "tall_wigglewood_door";
    
    public static String[] getNames(DoorSeries series) {
    	switch(series) {
    		case VANILLA:
    	        return new String[] {
    	                NAME_OAK, NAME_SPRUCE, NAME_BIRCH, NAME_JUNGLE, NAME_ACACIA, NAME_DARK_OAK,
    	                NAME_IRON, NAME_CRIMSON, NAME_WARPED/*, NAME_MANGROVE*/ };
		    case BOP:
		        return new String[] {
		                NAME_BOP_CHERRY, NAME_BOP_DEAD, NAME_BOP_FIR, NAME_BOP_HELLBARK, NAME_BOP_JACARANDA, NAME_BOP_MAGIC,
		                NAME_BOP_MAHOGANY, NAME_BOP_PALM, NAME_BOP_REDWOOD, NAME_BOP_UMBRAN, NAME_BOP_WILLOW };
		    case BYG:
		        return new String[] {
		                NAME_BYG_ASPEN, NAME_BYG_BAOBAB, NAME_BYG_BLUE_ENCHANTED, NAME_BYG_BULBIS, NAME_BYG_CHERRY, NAME_BYG_CIKA, NAME_BYG_CYPRESS, NAME_BYG_EBONY, 
		                NAME_BYG_EMBUR, NAME_BYG_ETHER, NAME_BYG_FIR, NAME_BYG_FLORUS, NAME_BYG_GREEN_ENCHANTED, NAME_BYG_HOLLY, NAME_BYG_IMPARIUS, NAME_BYG_IRONWOOD, 
		                NAME_BYG_JACARANDA, NAME_BYG_LAMENT, NAME_BYG_MAHOGANY, NAME_BYG_MANGROVE, NAME_BYG_MAPLE, NAME_BYG_NIGHTSHADE, NAME_BYG_PALM, 
		                NAME_BYG_PINE, NAME_BYG_RAINBOW_EUCALYPTUS, NAME_BYG_REDWOOD, NAME_BYG_SKYRIS, NAME_BYG_SOUL_SHROOM, NAME_BYG_SYTHIAN, NAME_BYG_WILLOW, 
		                NAME_BYG_WITCH_HAZEL, NAME_BYG_ZELKOVA };
			case PREHISTORIC_FAUNA:
		        return new String[] {
		                NAME_ARAUCARIA, NAME_HEIDIPHYLLUM, NAME_LIRIODENDRITES, NAME_METASEQUOIA,
		                NAME_PROTOJUNIPEROXYLON, NAME_PROTOPICEOXYLON, NAME_ZAMITES };
		    case TWILIGHT_FOREST:
		        return new String[] {
		                NAME_CANOPY, NAME_DARKWOOD, NAME_TWILIGHT_MANGROVE, NAME_MINEWOOD,
		                NAME_SORTINGWOOD, NAME_TIMEWOOD, NAME_TRANSWOOD, NAME_TWILIGHT_OAK };
    		case ATMOSPHERIC:
    	        return new String[] { NAME_ASPEN, NAME_GRIMWOOD, NAME_KOUSA, NAME_MORADO, NAME_ROSEWOOD, NAME_YUCCA };
    		case AUTUMNITY:
    	        return new String[] { NAME_MAPLE };
    		case BAMBOO:
    	        return new String[] { NAME_BAMBOO };
    		case BUZZIER:
    	        return new String[] { NAME_HONEYCOMB };
    		case CAVERNS_CHASMS:
    	        return new String[] { NAME_AZALEA };
    		case ENDERGETIC:
    	        return new String[] { NAME_POISE };
    		case ENVIRONMENTAL:
    	        return new String[] { NAME_CHERRY, NAME_WILLOW, NAME_WISTERIA };
    		case UPGRADE_AQUATIC:
    	        return new String[] { NAME_DRIFTWOOD, NAME_RIVER, NAME_GLASS, NAME_TOOTH };
    		case ABUNDANCE:
    	        return new String[] { NAME_JACARANDA, NAME_REDBUD };
    		case BAYOU_BLUES:
    	        return new String[] { NAME_CYPRESS };
    		case ENH_MUSHROOMS:
    	        return new String[] { NAME_BROWN_MUSHROOM, NAME_RED_MUSHROOM, NAME_GLOWSHROOM };
    		case ARCHITECTS_PALETTE:
    	        return new String[] { NAME_TWISTED };
    		case BLOCKS_PLUS:
    	        return new String[] { NAME_BP_BAMBOO, NAME_BP_MUSHROOM, NAME_BP_COPPER, NAME_BP_GOLDEN, NAME_BP_DIAMOND, NAME_BP_EMERALD, NAME_BP_NETHERITE };
			case CREATE_ALLOYED:
		        return new String[] { NAME_STEEL, NAME_LOCKED_STEEL };
			case CREATE_DECO:
		        return new String[] { NAME_ANDESITE, NAME_BRASS, NAME_COPPER, NAME_ZINC, NAME_LOCKED_ANDESITE, NAME_LOCKED_BRASS, NAME_LOCKED_COPPER, NAME_LOCKED_ZINC };
			case DARKER_DEPTHS:
		        return new String[] { NAME_PETRIFIED };
			case DUSTRIAL_DECOR:
		        return new String[] { 
		        		NAME_CARDBOARD, NAME_CHAIN, NAME_INDUSTRIAL_IRON, NAME_IRON_BAR,
		        		NAME_PADDED, NAME_RUSTY_IRON, NAME_RUSTY_SHEET_METAL, NAME_SHEET_METAL };
    		case ECOLOGICS:
    	        return new String[] { NAME_ECO_AZALEA, NAME_ECO_FLOWERING_AZALEA, NAME_ECO_COCONUT, NAME_ECO_WALNUT };
			case HABITAT:
		        return new String[] { NAME_FAIRY_RING_MUSHROOM };
			case OUTER_END:
		        return new String[] { NAME_AZURE };
			case POKECUBE:
		        return new String[] { 
		        		NAME_POKECUBE_ENIGMA, NAME_POKECUBE_LEPPA, NAME_POKECUBE_NANAB, NAME_POKECUBE_ORAN, NAME_POKECUBE_PECHA, NAME_POKECUBE_SITRUS,
		        		NAME_POKECUBE_AGED, NAME_POKECUBE_CONCRETE, NAME_POKECUBE_CORRUPTED, NAME_POKECUBE_DISTORTIC, NAME_POKECUBE_INVERTED, NAME_POKECUBE_MIRAGE, NAME_POKECUBE_TEMPORAL };
			case PREMIUM_WOOD:
		        return new String[] { NAME_PW_MAGIC, NAME_PW_MAPLE, NAME_PW_PURPLE_HEART, NAME_PW_SILVERBELL, NAME_PW_TIGER, NAME_PW_WILLOW };
			case QUARK:
		        return new String[] { NAME_QUARK_AZALEA, NAME_QUARK_BLOSSOM };
			case SUPPLEMENTARIES:
		        return new String[] { NAME_GOLD, NAME_SILVER, NAME_LEAD, NAME_NETHERITE };
			case UNDERGARDEN:
		        return new String[] { NAME_GRONGLE, NAME_SMOGSTEM, NAME_WIGGLEWOOD };
    		default:
    	        throw new NotImplementedException("Please don't use the tall version of DoorSeries.");
    	}
    }

    public static final EnumProperty<TripleBlockPart> THIRD = DDBlockStateProperties.TRIPLE_BLOCK_THIRD;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);


    public TallDoorBlock(Block from) {
        super(Properties.copy(from));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE).setValue(HINGE, DoorHingeSide.LEFT).setValue(POWERED, Boolean.FALSE).setValue(THIRD, TripleBlockPart.LOWER));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        TripleBlockPart tripleblockpart = stateIn.getValue(THIRD);
        if (facing.getAxis() == Direction.Axis.Y && ((tripleblockpart == TripleBlockPart.LOWER == (facing == Direction.UP)) || tripleblockpart == TripleBlockPart.MIDDLE)) {
            if (facingState.getBlock() == this && facingState.getValue(THIRD) != tripleblockpart) {
                return stateIn.setValue(FACING, facingState.getValue(FACING)).setValue(OPEN, facingState.getValue(OPEN)).setValue(HINGE, facingState.getValue(HINGE)).setValue(POWERED, facingState.getValue(POWERED));
            } else {
                return Blocks.AIR.defaultBlockState();
            }
        } else {
            if (tripleblockpart == TripleBlockPart.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos)) {
                return Blocks.AIR.defaultBlockState();
            } else {
                return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            BlockPos otherPos1 = pos;
            BlockPos otherPos2 = pos;
            TripleBlockPart tripleblockpart = state.getValue(THIRD);
            switch (tripleblockpart) {
                case LOWER:
                    otherPos1 = pos.above();
                    otherPos2 = pos.above(2);
                    break;
                case MIDDLE:
                    otherPos1 = pos.below();
                    otherPos2 = pos.above();
                    break;
                case UPPER:
                    otherPos1 = pos.below(2);
                    otherPos2 = pos.below();
                    break;
            }
            BlockState blockstate1 = level.getBlockState(otherPos1);
            BlockState blockstate2 = level.getBlockState(otherPos2);
            if (blockstate1.getBlock() == state.getBlock() && blockstate1.getValue(THIRD) == TripleBlockPart.LOWER) {
                level.setBlock(otherPos1, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, otherPos1, Block.getId(blockstate1));
            }
            if (blockstate2.getBlock() == state.getBlock() && blockstate2.getValue(THIRD) == TripleBlockPart.LOWER) {
                level.setBlock(otherPos2, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, otherPos1, Block.getId(blockstate1));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    private int getCloseSound() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getOpenSound() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        if (blockpos.getY() < context.getLevel().getMaxBuildHeight() - 2 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context)) {
            Level level = context.getLevel();
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(HINGE, this.getHinge(context)).setValue(POWERED, flag).setValue(OPEN, flag).setValue(THIRD, TripleBlockPart.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(THIRD, TripleBlockPart.MIDDLE), 3);
        level.setBlock(pos.above().above(), state.setValue(THIRD, TripleBlockPart.UPPER), 3);
    }

    private DoorHingeSide getHinge(BlockPlaceContext context) {
        BlockGetter BlockGetter = context.getLevel();
        BlockPos placePos = context.getClickedPos();
        Direction behindDir = context.getHorizontalDirection(); // Behind
        BlockPos placePosAbove = placePos.above();
        Direction leftDir = behindDir.getCounterClockWise(); // Left
        BlockPos leftPos = placePos.relative(leftDir); // Left Pos
        BlockState leftBlockstate = BlockGetter.getBlockState(leftPos); // Left BlockState
        BlockPos leftPosAbove = placePosAbove.relative(leftDir); // Left Up Pos
        BlockState leftAboveBlockstate = BlockGetter.getBlockState(leftPosAbove); // Left Up BlockState
        Direction rightDir = behindDir.getClockWise(); // Right
        BlockPos rightPos = placePos.relative(rightDir); // Right Pos
        BlockState rightBlockstate = BlockGetter.getBlockState(rightPos); // Right Blockstate
        BlockPos rightPosAbove = placePosAbove.relative(rightDir); // Right Up Pos
        BlockState rightAboveBlockstate = BlockGetter.getBlockState(rightPosAbove); // Right Up Blockstate
        int i = (leftBlockstate.isCollisionShapeFullBlock(BlockGetter, leftPos) ? -1 : 0) + (leftAboveBlockstate.isCollisionShapeFullBlock(BlockGetter, leftPosAbove) ? -1 : 0) + (rightBlockstate.isCollisionShapeFullBlock(BlockGetter, rightPos) ? 1 : 0) + (rightAboveBlockstate.isCollisionShapeFullBlock(BlockGetter, rightPosAbove) ? 1 : 0);
        boolean leftIsLowerOfSameType = leftBlockstate.getBlock() == this && leftBlockstate.getValue(THIRD) == TripleBlockPart.LOWER;
        boolean rightIsLowerOfSameType = rightBlockstate.getBlock() == this && rightBlockstate.getValue(THIRD) == TripleBlockPart.LOWER;
        if ((!leftIsLowerOfSameType || rightIsLowerOfSameType) && i <= 0) {
            if ((!rightIsLowerOfSameType || leftIsLowerOfSameType) && i >= 0) {
                int j = behindDir.getStepX();
                int k = behindDir.getStepZ();
                Vec3 vec3d = context.getClickLocation();
                double d0 = vec3d.x - (double)placePos.getX();
                double d1 = vec3d.z - (double)placePos.getZ();
                return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
    	if (this.material == Material.METAL && !state.is(DDTags.HAND_OPENABLE_TALL_METAL_DOORS)) {
            return InteractionResult.PASS;
        } 
    	else {
        	if (this == DDBlocks.TALL_GOLD_DOOR && state.getValue(POWERED)) {
        		return InteractionResult.PASS;
        	}
        	if (this == DDBlocks.TALL_SILVER_DOOR && !state.getValue(POWERED)) {
        		return InteractionResult.PASS;
        	}
        	tryOpenDoubleDoor(level, state, pos);
            state = state.cycle(OPEN);
            level.setBlock(pos, state, 10);
            level.levelEvent(player, state.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
            level.gameEvent(player, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            if (DDBlocks.TALL_TOOTH_DOOR != null && this == DDBlocks.TALL_TOOTH_DOOR) {
            	level.scheduleTick(pos, this, 20);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }
    
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		if (!level.isClientSide) {
			state = state.cycle(OPEN);
			level.setBlock(pos, state, 10);
			level.levelEvent(null, state.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
		}
	}

    public void toggleDoor(Level level, BlockPos pos, boolean open) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.getBlock() == this && blockstate.getValue(OPEN) != open) {
            level.setBlock(pos, blockstate.setValue(OPEN, open), 10);
            if (blockstate.getValue(THIRD) == TripleBlockPart.UPPER) {
                BlockState middle = level.getBlockState(pos.below());
                BlockState bottom = level.getBlockState(pos.below(2));
                if (middle.getBlock() == this) {
                    level.setBlock(pos.below(), middle.setValue(OPEN, open), 10);
                }
                if (bottom.getBlock() == this) {
                    level.setBlock(pos.below(2), middle.setValue(OPEN, open), 10);
                }
            }
            this.playSound(level, pos, open);
        }
    }

	public boolean isOpen(BlockState state) {
		return state.getValue(OPEN);
	}

	public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean open) {
		if (state.is(this) && state.getValue(OPEN) != open) {
			level.setBlock(pos, state.setValue(OPEN, Boolean.valueOf(open)), 10);
			this.playSound(level, pos, open);
			level.gameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
		}
 	}
    
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean flag = level.hasNeighborSignal(pos);
        if (!flag) {
            switch(state.getValue(THIRD)) {
                case LOWER:
                    flag = level.hasNeighborSignal(pos.relative(Direction.UP)) || level.hasNeighborSignal(pos.relative(Direction.UP, 2));
                    break;
                case MIDDLE:
                    flag = level.hasNeighborSignal(pos.relative(Direction.DOWN)) || level.hasNeighborSignal(pos.relative(Direction.UP));
                    break;
                case UPPER:
                    flag = level.hasNeighborSignal(pos.relative(Direction.DOWN)) || level.hasNeighborSignal(pos.relative(Direction.DOWN, 2));
                    break;
            }
        }
        if (blockIn != this && flag != state.getValue(POWERED)) {
        	if (this == DDBlocks.TALL_GOLD_DOOR || this == DDBlocks.TALL_SILVER_DOOR || this == DDBlocks.TALL_LEAD_DOOR) {
        		level.setBlock(pos, state.setValue(POWERED, flag), 2);
        	}
        	else {
	            if (flag != state.getValue(OPEN)) {
	                this.playSound(level, pos, flag);
	            }
	            tryOpenDoubleDoor(level, state, pos);
	            level.setBlock(pos, state.setValue(POWERED, flag).setValue(OPEN, flag), 2);
        	}
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean result;
        BlockPos below = pos.below();
        BlockPos below2 = below.below();
        BlockState belowState = level.getBlockState(below);
        BlockState below2State = level.getBlockState(below2);
        if (state.getValue(THIRD) == TripleBlockPart.LOWER) {
            result = belowState.isFaceSturdy(level, below, Direction.UP);
            return result;
        } else if (state.getValue(THIRD) == TripleBlockPart.MIDDLE) {
            result = belowState.getBlock() == this;
            return result;
        } else {
            result = belowState.getBlock() == this && below2State.getBlock() == this;
            return result;
        }
    }

    private void playSound(Level level, BlockPos pos, boolean isOpen) {
        level.levelEvent(null, isOpen ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(THIRD) == TripleBlockPart.LOWER ? 0 : state.getValue(THIRD) == TripleBlockPart.MIDDLE ? 1 : 2).getY(), pos.getZ());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(THIRD, FACING, OPEN, HINGE, POWERED);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        boolean flag = !state.getValue(OPEN);
        boolean flag1 = state.getValue(HINGE) == DoorHingeSide.RIGHT;
        switch(direction) {
            case EAST:
            default:
                return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
            case SOUTH:
                return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
            case WEST:
                return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
            case NORTH:
                return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
        }
    }

    public boolean allowsMovement(BlockState state, BlockGetter level, BlockPos pos, BlockPathTypes type) {
        switch(type) {
            case WALKABLE:
                return state.getValue(OPEN);
            case WATER:
                return false;
            case OPEN:
                return state.getValue(OPEN);
            default:
                return false;
        }
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.getRotation(state.getValue(FACING))).cycle(HINGE);
    }

    // TODO: Figure out if Zombie breaking Tall Doors is feasible...
    public static boolean isWoodenDoor(Level level, BlockPos pos) {
    	return isWoodenDoor(level.getBlockState(pos));
    }

	public static boolean isWoodenDoor(BlockState state) {
		return state.getBlock() instanceof TallDoorBlock && (state.is(DDTags.TALL_WOODEN_DOORS));
	}
    
    //Double Doors Compatibility
	public static void tryOpenDoubleDoor(Level world, BlockState state, BlockPos pos) {
        if (Compats.DOUBLE_DOORS_INSTALLED || Compats.hasQuarkDoubleDoorsModule()) {
            Direction direction = state.getValue(TallDoorBlock.FACING);
            boolean isOpen = state.getValue(TallDoorBlock.OPEN);
            DoorHingeSide isMirrored = state.getValue(TallDoorBlock.HINGE);
            BlockPos mirrorPos = pos.relative(isMirrored == DoorHingeSide.RIGHT ? direction.getCounterClockWise() : direction.getClockWise());
            BlockPos doorPos = state.getValue(TallDoorBlock.THIRD) == TripleBlockPart.LOWER ? mirrorPos : mirrorPos.below();
            BlockState other = world.getBlockState(doorPos);
            if (other.getBlock() == state.getBlock() && other.getValue(TallDoorBlock.FACING) == direction && !other.getValue(TallDoorBlock.POWERED) &&  other.getValue(TallDoorBlock.OPEN) == isOpen && other.getValue(TallDoorBlock.HINGE) != isMirrored) {
                BlockState newState = other.cycle(TallDoorBlock.OPEN);
                world.setBlock(doorPos, newState, 10);
            }
        }
    }
}