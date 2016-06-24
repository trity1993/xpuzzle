package cc.trity.xpuzzle.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import cc.trity.xpuzzle.bean.ImagePiece;

/**
 * Created by trity on 24/6/16.
 */
public class ImageSplitUtils {
    /**
     * 将bitmap进行分割成piece*piece份
     * @param bitmap
     * @param piece
     * @return
     */
    public static List<ImagePiece> splitImage(Bitmap bitmap,int piece){
        List<ImagePiece> imagePieceList=new ArrayList<>();

        //获取图片的宽高
        int imageWidth=bitmap.getWidth();
        int imageHeight=bitmap.getHeight();

        //得到每个图片的大小
        int pieceWidth=Math.min(imageWidth,imageHeight)/piece;

        //进行切割
        for(int i=0;i<piece;i++){
            for (int j = 0; j < piece; j++) {
                ImagePiece imagePiece=new ImagePiece();
                imagePiece.setIndex(j+i*piece);//将二维数组使用一维数组的存储方式

                //设置切割的坐标
                int x = j * pieceWidth;
                int y = i * pieceWidth;

                imagePiece.setBitmap(Bitmap.createBitmap(bitmap,x,y,pieceWidth,pieceWidth));

                imagePieceList.add(imagePiece);
            }
        }
        return imagePieceList;
    }
}
