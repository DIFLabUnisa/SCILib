package it.unisa.di.dif.pattern;

import it.unisa.di.dif.utils.CHILogger;
import it.unisa.di.dif.utils.Constant;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends GenericPattern{
    private boolean isFiltered;

    public Image(File f) throws IOException {
        super();

        BufferedImage image;

        try {
            image= ImageIO.read(f);

            int x,x1;

            int numeroRighe=image.getHeight();
            int numeroColonne=image.getWidth();

            setFiltered(false);

            float[][] canale_Red = new float[numeroRighe][numeroColonne];
            float[][] canale_Green = new float[numeroRighe][numeroColonne];
            float[][] canale_Blue = new float[numeroRighe][numeroColonne];

            for(int i=0;i<numeroRighe;i++){
                for(int j=0;j<numeroColonne;j++){
                    x=image.getRGB(j, i); //il metodo prende prima le colonne e poi le righe
                    x1=x&0x7FFFFFFF;
                    canale_Red[i][j]=((x1/256)/256)%256;
                    canale_Green[i][j]=(x1/256)%256;
                    canale_Blue[i][j]=x1%256;
                }
            }

            this.setRedChannel(new ColorChannel(canale_Red, ColorChannel.Channel.RED));
            this.setGreenChannel(new ColorChannel(canale_Green, ColorChannel.Channel.GREEN));
            this.setBlueChannel(new ColorChannel(canale_Blue, ColorChannel.Channel.BLUE));

        } catch (IOException e) {

            Constant constant = Constant.getInstance();
            if(constant.isWriteMessageOnStderr())
                e.printStackTrace();

            CHILogger logger = CHILogger.getInstance();
            logger.log.fatal("Impossibile leggere il file d'immagine "+f+".");

            throw e;
        }
    }

    public Image(String path) throws IOException {
        this(new File(path));
    }

    public boolean storeInFile(File f){
        try{

            BufferedImage immagine = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);

            int r = 0;
            int g = 0;
            int b = 0;
            int col = 0;

            for(int i=0; i<this.getHeight(); i++)
                for(int j=0; j<this.getWidth(); j++){

                    r = this.getValueAsInteger(i, j, ColorChannel.Channel.RED); // red component 0...255
                    g = this.getValueAsInteger(i, j, ColorChannel.Channel.GREEN); // green component 0...255
                    b = this.getValueAsInteger(i, j, ColorChannel.Channel.BLUE); // blue component 0...255

                    col = (r << 16) | (g << 8) | b;

                    immagine.setRGB(j, i, col);
                }


            ImageIO.write(immagine, "PNG", f); //Non voglio la compressione

        }

        catch(Exception e){
            return false; //ERRORE
        }

        return true; //Nessun errore (Scrittura riuscita)
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public void setFiltered(boolean filtered) {
        isFiltered = filtered;
    }

    @Override
    public String toString() {

        String msg="";

        if(this.isFiltered())
            msg="immagine filtrata";
        else
            msg="immagine originaria (non filtrata)";

        return "ImmagineFile [Base="+ this.getWidth()+", Altezza=" + this.getHeight() + ", "+msg + "]";
    }
}
