package rsc.uiClasses;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

public class CustomPainter extends SynthPainter {

    @Override
    public void paintPanelBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
    	RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 15, 15);
        Color col = context.getStyle().getColor(context, ColorType.BACKGROUND);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(col);
        g2.fill(rect);
    }

    @Override
    public void paintListBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
        try {
            // load image
            BufferedImage bim = ImageIO.read(getClass().getResourceAsStream("/img/duke.jpg"));
            // create image to use for painting
            BufferedImage used = new BufferedImage(bim.getWidth(), bim.getHeight(), BufferedImage.TRANSLUCENT);
            // create graphics for panting from used image
            Graphics2D g2 = used.createGraphics();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            g2.drawImage(bim, null, 0, 0);
            g2.dispose();
            // now, draw transparent image
            g2 = (Graphics2D) g;
            g2.drawImage(used.getScaledInstance(w, h, Image.SCALE_FAST), 0, 0, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void paintButtonBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        Color col = context.getStyle().getColor(context, ColorType.BACKGROUND);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(col);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(rect);
    }

    
    @Override
    public void paintButtonBorder(SynthContext context, Graphics g, int x, int y, int w, int h) {
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.draw(rect);
        
    }
    
    @Override
    public void paintTextFieldBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        Color col = context.getStyle().getColor(context, ColorType.BACKGROUND);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(col);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(rect);
    }
    
    public void paintTextFieldBorder(SynthContext context, Graphics g, int x, int y, int w, int h) {
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.draw(rect);
        
    }
    
}