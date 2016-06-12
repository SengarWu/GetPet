package com.example.administrator.getpet.utils;

import com.example.administrator.getpet.R;

/**
 * Created by Administrator on 2016/6/5.
 */
public class GetPictureUtils {
    private static int[] image;

    public static int[] GetPicture(int count)
    {
        image = new int[count];
        for (int i = 0; i < count ; i++) {
            switch (i%20)
            {
                case 0:
                    image[i] = R.mipmap.pet_picture20;
                    break;
                case 1:
                    image[i] = R.mipmap.pet_picture1;
                    break;
                case 2:
                    image[i] = R.mipmap.pet_picture2;
                    break;
                case 3:
                    image[i] = R.mipmap.pet_picture3;
                    break;
                case 4:
                    image[i] = R.mipmap.pet_picture4;
                    break;
                case 5:
                    image[i] = R.mipmap.pet_picture5;
                    break;
                case 6:
                    image[i] = R.mipmap.pet_picture6;
                    break;
                case 7:
                    image[i] = R.mipmap.pet_picture7;
                    break;
                case 8:
                    image[i] = R.mipmap.pet_picture8;
                    break;
                case 9:
                    image[i] = R.mipmap.pet_picture9;
                    break;
                case 10:
                    image[i] = R.mipmap.pet_picture10;
                    break;
                case 11:
                    image[i] = R.mipmap.pet_picture11;
                    break;
                case 12:
                    image[i] = R.mipmap.pet_picture12;
                    break;
                case 13:
                    image[i] = R.mipmap.pet_picture13;
                    break;
                case 14:
                    image[i] = R.mipmap.pet_picture14;
                    break;
                case 15:
                    image[i] = R.mipmap.pet_picture15;
                    break;
                case 16:
                    image[i] = R.mipmap.pet_picture16;
                    break;
                case 17:
                    image[i] = R.mipmap.pet_picture17;
                    break;
                case 18:
                    image[i] = R.mipmap.pet_picture18;
                    break;
                case 19:
                    image[i] = R.mipmap.pet_picture19;
                    break;
                default:
                    image[i] = R.mipmap.pet_picture1;
            }
        }
        return image;
    }
}
