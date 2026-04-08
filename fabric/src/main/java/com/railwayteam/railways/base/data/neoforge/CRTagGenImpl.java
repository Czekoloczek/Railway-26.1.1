package com.railwayteam.railways.base.data.neoforge;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagAppender;
import net.minecraft.tags.TagKey;

/**
 * Fabric implementation of CRTagGenImpl.
 * Identical behaviour on both platforms – uses Registrate's tag provider.
 */
public class CRTagGenImpl {
    public static <T> TagAppender<T> tagAppender(RegistrateTagsProvider<T> prov, TagKey<T> tag) {
        return prov.addTag(tag);
    }
}
