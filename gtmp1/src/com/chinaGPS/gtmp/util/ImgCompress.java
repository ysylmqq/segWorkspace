package com.chinaGPS.gtmp.util;

import java.io.*; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.awt.*; 
import com.sun.image.codec.jpeg.*; 
import java.awt.image.BufferedImage; 
/** 
* 图片压缩
* @author Jiangpin 
*/ 
public class ImgCompress { 
	private ImgCompress(){
		
	}
    /** 
    * 图片压缩处理方法
    * @param file 图片  
    * @param fileName 图片名 
    * @param w 目标宽 
    * @param h 目标高 
    * @param per 百分比 
    */ 
	public static File toSmallerImg(File file,String fileName,int w,int h,float per){ 
        Image src; 
        String img_midname=file.getParent()+"\\"+RandomFileName.newFileName(fileName); 
        try { 
        	src = javax.imageio.ImageIO.read(file); //构造Image对象 
        	int old_w=src.getWidth(null); //得到源图宽 
        	int old_h=src.getHeight(null); 
        	int new_w=0; 
    		int new_h=0; //得到源图长 

    		double w2=(old_w*1.00)/(w*1.00); 
    		double h2=(old_h*1.00)/(h*1.00); 

    		//图片跟据长宽留白，成一个正方形图。 
    		BufferedImage oldpic; 
    		if(old_w>old_h) 
    		{ 
    			oldpic=new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB); 
    		}else{
    			if(old_w<old_h){ 
    				oldpic=new BufferedImage(old_h,old_h,BufferedImage.TYPE_INT_RGB); 
    			}else{ 
    				oldpic=new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB); 
    			} 
    		} 

			Graphics2D g = oldpic.createGraphics(); 
			g.setColor(Color.white); 
		    if(old_w>old_h) 
		    { 
		        g.fillRect(0, 0, old_w, old_w); 
		        g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null); 
		    }else{ 
		        if(old_w<old_h){ 
		        	g.fillRect(0,0,old_h,old_h); 
		        	g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null); 
		        }else{ 
		            //g.fillRect(0,0,old_h,old_h); 
		            g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null); 
		        } 
		    }              
		    g.dispose(); 
		    src = oldpic; 
		    //图片调整为方形结束 
		    if(old_w>w)
		    { 
		    	new_w=(int)Math.round(old_w/w2); 
		    }else{ 
		    	new_w=old_w; 
		    }
		    if(old_h>h)
		    { 
		    	new_h=(int)Math.round(old_h/h2);//计算新图长宽 
		    }else{ 
		    	new_h=old_h; 
		    }
		    BufferedImage tag = new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB);        
		    //tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图 
		    tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null); 
		    FileOutputStream newimage=new FileOutputStream(img_midname); //输出到文件流 
		    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage); 
		    JPEGEncodeParam jep=JPEGCodec.getDefaultJPEGEncodeParam(tag); 
		    /* 压缩质量 */ 
		    jep.setQuality(per, true); 
		    encoder.encode(tag, jep);
		    //encoder.encode(tag); //近JPEG编码 
		    newimage.close(); 
        } catch (IOException ex) { 
        	Logger.getLogger(ImgCompress.class.getName()).log(Level.SEVERE, null, ex); 
        } 
        
        return new File(img_midname);
    } 
    
    public static void main(String args[]){ 
        //String n="0e5465fc-025a-458d-8383-e9ced0c1e728.jpg"; 
       /* String f="c:\\test"; 
        File file=new File(f); 
        if(file.exists()) 
        { 
            File[] filelist=file.listFiles(); 
            for(int i=0;i<filelist.length;i++) 
            { 
                String n=filelist[i].getName(); 
                System.out.println(n);
                ImgCompress.toSmallerImg(filelist[i],n,427,280,(float)0.8); 
//                compress.toSmallerImg(filelist[i],n,45,45,(float)0.7); 
//                compress.toSmallerImg(filelist[i],n,116,100,(float)0.7); 
            } 
        } */
        String f="C:\\Users\\Administrator\\Pictures\\5119331_m.jpg"; 
        File file=new File(f); 
        
        if(file.exists()) {
            file=ImgCompress.toSmallerImg(file,file.getName(),16,16,(float)0.8);
        }
        System.out.println(file.getName());
    }
} 

