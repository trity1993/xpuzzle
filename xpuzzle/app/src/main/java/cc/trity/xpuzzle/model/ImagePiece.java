package cc.trity.xpuzzle.model;

import android.graphics.Bitmap;

/**
 * Created by trity on 24/6/16.
 */
public class ImagePiece {
    private int index;
    private Bitmap bitmap;

    public ImagePiece() {
    }

    public ImagePiece(int index, Bitmap bitmap) {
        this.index = index;
        this.bitmap = bitmap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
