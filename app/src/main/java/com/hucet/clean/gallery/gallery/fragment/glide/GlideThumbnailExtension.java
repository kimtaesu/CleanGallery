package com.hucet.clean.gallery.gallery.fragment.glide;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hucet.clean.gallery.config.ConstantsKt;

/**
 * Created by taesu on 2017-11-21.
 */

@GlideExtension
public class GlideThumbnailExtension {

    private GlideThumbnailExtension() {
    }

    private static final RequestOptions REQUEST_OPTIONS_THUMBNAIL =
            RequestOptions.overrideOf(ConstantsKt.getGLIDE_OVERRIVE_SIZE());

    @GlideType(Drawable.class)
    public static void asThumbnail(RequestBuilder<Drawable> requestBuilder) {
        requestBuilder
                .apply(REQUEST_OPTIONS_THUMBNAIL)
                .thumbnail(0.25f);
    }
}