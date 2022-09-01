package top.mtserver.commands.Arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.DefaultPosArgument;
import net.minecraft.command.argument.LookingPosArgument;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class ChunkPosArgument implements ArgumentType<PosArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5");
    public static final SimpleCommandExceptionType UNLOADED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.pos.unloaded"));

    public static ChunkPosArgument ChunkPos() {
        return new ChunkPosArgument();
    }

    public static ChunkPos getLoadedChunkPos(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
        BlockPos blockPos = context.getArgument(name, PosArgument.class).toAbsoluteBlockPos(context.getSource());
        if (!context.getSource().getWorld().isChunkLoaded(blockPos)) {
            throw UNLOADED_EXCEPTION.create();
        } else {
            context.getSource().getWorld();
            return new ChunkPos(blockPos);
        }
    }

    public PosArgument parse(StringReader stringReader) throws CommandSyntaxException {
        return stringReader.canRead() && stringReader.peek() == '^' ? LookingPosArgument.parse(stringReader) : DefaultPosArgument.parse(stringReader);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (!(context.getSource() instanceof CommandSource)) {
            return Suggestions.empty();
        } else {
            String string = builder.getRemaining();
            Object collection;
            if (!string.isEmpty() && string.charAt(0) == '^') {
                CommandSource.RelativePosition ZERO_LOCAL = new CommandSource.RelativePosition("^", "^", "^");
                collection = Collections.singleton(ZERO_LOCAL);
            } else {
                collection = ((CommandSource) context.getSource()).getBlockPositionSuggestions();
            }

            return CommandSource.suggestPositions(string, (Collection) collection, builder, CommandManager.getCommandValidator(this::parse));
        }
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
