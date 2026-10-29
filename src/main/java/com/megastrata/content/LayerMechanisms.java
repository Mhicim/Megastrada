package com.megastrata.content;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class LayerMechanisms {

    public static class WaterPumpControllerBlock extends Block {
        public WaterPumpControllerBlock(Properties props) { super(props); }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            if (!level.isClientSide) {
                int drained = 0;
                for (BlockPos target : BlockPos.betweenClosed(pos.offset(-12, -8, -12), pos.offset(12, 12, 12))) {
                    if (level.getBlockState(target).is(Blocks.WATER)) {
                        level.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
                        drained++;
                    }
                }
                player.displayClientMessage(Component.literal("Idle pump cycle halted. Local sector reservoir drained (" + drained + " blocks).").withStyle(ChatFormatting.AQUA), false);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    public static class FactoryAssemblerBlock extends Block {
        public FactoryAssemblerBlock(Properties props) { super(props); }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            if (!level.isClientSide) {
                ItemStack held = player.getItemInHand(hand);
                if (held.is(ModRegistry.STRUCTURAL_ALLOY.get())) {
                    held.shrink(1);
                    player.getInventory().add(new ItemStack(ModRegistry.DRIVE_RELAY_UNIT.get(), 1));
                    player.displayClientMessage(Component.literal("Production cycle completed: Drive Relay Unit assembled.").withStyle(ChatFormatting.GOLD), true);
                } else {
                    player.displayClientMessage(Component.literal("Line running idle stamping blank plates. Insert Structural Alloy to manufacture relay.").withStyle(ChatFormatting.GRAY), true);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    public static class ArchiveTerminalBlock extends Block {
        private static final String[] LOGS = {
            "[Log #409-A]: Sector is under scheduled automated maintenance. Elevator operational.",
            "[Log #409-B]: Data mismatch — maintenance subsystem disconnected 412 years ago. Elevator offline for 3 weeks.",
            "[Archival Schematic]: Sealed transport hub located on adjacent mainline (+512, Y=-400, -256)."
        };

        public ArchiveTerminalBlock(Properties props) { super(props); }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            if (!level.isClientSide) {
                Random rand = new Random(pos.asLong() ^ level.getGameTime() / 1200);
                String entry = LOGS[rand.nextInt(LOGS.length)];
                player.displayClientMessage(Component.literal(entry).withStyle(ChatFormatting.BLUE), false);

                if (player.getItemInHand(hand).is(ModRegistry.MEMORY_MODULE.get())) {
                    player.getItemInHand(hand).shrink(1);
                    player.getInventory().add(new ItemStack(ModRegistry.ARCHIVAL_MAP.get()));
                    player.displayClientMessage(Component.literal("Infrastructure coordinates written to Archival Map.").withStyle(ChatFormatting.GREEN), true);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }
}
