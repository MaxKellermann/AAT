package ch.bailu.aat.preferences.map;

import android.content.Context;

import ch.bailu.aat.preferences.SolidBoolean;
import ch.bailu.aat.util.ToDo;

public class SolidDem3EnableDownload extends SolidBoolean {
    public SolidDem3EnableDownload(Context c) {
        super(c, SolidDem3EnableDownload.class.getSimpleName());
    }

    @Override
    public String getLabel() {
        return ToDo.translate("Enable automatic download");
    }
}
