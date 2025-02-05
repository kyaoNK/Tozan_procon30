package user.example.com.tozandatacollectapp.sub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;

/**
 * 画像変換クラス
 *
 */
public class BitmapUtil {

    /**
     * 画像生成
     * 表示サイズ合わせて画像生成時に可能なかぎり縮小して生成します。
     *
     * @param file ファイル
     * @param size 大きさ
     * @return 生成Bitmap
     */
    public static Bitmap createBitmap(File file, int size) {

        BitmapFactory.Options option = new BitmapFactory.Options();

        // 情報のみ読み込む
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), option);

        if (option.outWidth < size || option.outHeight < size) {
            // 縦、横のどちらかが指定値より小さい場合は普通にBitmap生成
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        float scaleWidth = ((float) size) / option.outWidth;
        float scaleHeight = ((float) size) / option.outHeight;

        int newSize = 0;
        int oldSize = 0;
        newSize = size;
        if (scaleWidth > scaleHeight) {
            oldSize = option.outWidth;
        } else {
            oldSize = option.outHeight;
        }

        // option.inSampleSizeに設定する値を求める
        // option.inSampleSizeは2の乗数のみ設定可能
        int sampleSize = 1;
        int tmpSize = oldSize;
        while (tmpSize > newSize) {
            sampleSize = sampleSize * 2;
            tmpSize = oldSize / sampleSize;
        }
        if (sampleSize != 1) {
            sampleSize = sampleSize / 2;
        }

        option.inJustDecodeBounds = false;
        option.inSampleSize = sampleSize;


        return BitmapFactory.decodeFile(file.getAbsolutePath(), option);
    }

    /**
     * 画像リサイズ
     * @param bitmap 変換対象ビットマップ
     * @param newWidth 変換サイズ横
     * @param newHeight 変換サイズ縦
     * @return 変換後Bitmap
     */
    public static Bitmap resize(Bitmap bitmap, int newWidth, int newHeight) {

        if (bitmap == null) {
            return null;
        }

        int oldWidth = bitmap.getWidth();
        int oldHeight = bitmap.getHeight();

        if (oldWidth < newWidth && oldHeight < newHeight) {
            // 縦も横も指定サイズより小さい場合は何もしない
            return bitmap;
        }

        float scaleWidth = ((float) newWidth) / oldWidth;
        float scaleHeight = ((float) newHeight) / oldHeight;
        float scaleFactor = Math.min(scaleWidth, scaleHeight);

        Matrix scale = new Matrix();
        scale.postScale(scaleFactor, scaleFactor);

        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, oldWidth, oldHeight, scale, false);
        bitmap.recycle();

        return resizeBitmap;

    }
}