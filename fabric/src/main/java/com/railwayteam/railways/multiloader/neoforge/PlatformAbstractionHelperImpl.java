package com.railwayteam.railways.multiloader.neoforge;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * Fabric implementation of PlatformAbstractionHelperImpl.
 */
public class PlatformAbstractionHelperImpl {

    /**
     * Returns the fuel burn time for the given item in ticks.
     *
     * {@link AbstractFurnaceBlockEntity#getFuel()} returns the same data the
     * vanilla furnace uses, and includes all values registered via Fabric's
     * fuel-registration extension events.
     */
    public static int getBurnTime(Item item) {
        return AbstractFurnaceBlockEntity.getFuel().getOrDefault(item, 0);
    }

    /**
     * Returns a Brigadier {@link ArgumentType} for the given enum class.
     *
     * <p>The argument type:
     * <ul>
     *   <li>parses input case-insensitively against all enum constant names;</li>
     *   <li>provides tab-completion suggestions from all constant names (lower-case);</li>
     *   <li>throws a {@link CommandSyntaxException} for unknown values, listing the
     *       valid options in the error message.</li>
     * </ul>
     */
    public static <T extends Enum<T>> ArgumentType<T> enumArgument(Class<T> enumClass) {
        return new EnumArgumentType<>(enumClass);
    }

    // -----------------------------------------------------------------------
    // Inner implementation
    // -----------------------------------------------------------------------

    private static final class EnumArgumentType<T extends Enum<T>> implements ArgumentType<T> {

        private static final DynamicCommandExceptionType ERROR_INVALID_VALUE =
                new DynamicCommandExceptionType(msg -> Component.literal(String.valueOf(msg)));

        private final Class<T> enumClass;
        private final T[] constants;

        EnumArgumentType(Class<T> enumClass) {
            this.enumClass = enumClass;
            this.constants = enumClass.getEnumConstants();
        }

        @Override
        public T parse(StringReader reader) throws CommandSyntaxException {
            String input = reader.readString();
            // Try exact match first, then case-insensitive.
            for (T constant : constants) {
                if (constant.name().equalsIgnoreCase(input)) {
                    return constant;
                }
            }
            String valid = buildValidList();
            throw ERROR_INVALID_VALUE.createWithContext(reader,
                    "Unknown " + enumClass.getSimpleName() + " value '" + input
                    + "'. Valid values: " + valid);
        }

        @Override
        public <S> CompletableFuture<Suggestions> listSuggestions(
                CommandContext<S> context, SuggestionsBuilder builder) {
            String remaining = builder.getRemainingLowerCase();
            for (T constant : constants) {
                String name = constant.name().toLowerCase(Locale.ROOT);
                if (name.startsWith(remaining)) {
                    builder.suggest(name);
                }
            }
            return builder.buildFuture();
        }

        @Override
        public Collection<String> getExamples() {
            return Arrays.stream(constants)
                    .limit(3)
                    .map(c -> c.name().toLowerCase(Locale.ROOT))
                    .toList();
        }

        private String buildValidList() {
            return String.join(", ", Arrays.stream(constants)
                    .map(c -> c.name().toLowerCase(Locale.ROOT))
                    .toList());
        }
    }
}
