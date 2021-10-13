package PrismaMod.actions.sfx.IllyaSFX.CardSFX.InstallSFX;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.DefaultMod.makeOrbPath;

public class InstallAssassinSFX extends AbstractSFX {
    private final Texture img;// = TextureLoader.getTexture(makeOrbPath("Testament_orb.png"));
    private float x = 0;
    private float y = 0;
    private float vX = 0;

    private static final float FADE_IN_TIME = 0.05F;
    private static final float FADE_OUT_TIME = 0.4F;
    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    public InstallAssassinSFX(){
        this.img = TextureLoader.getTexture(makeOrbPath("Testament_orb.png"));// 23
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 24
        this.x = (float) Settings.WIDTH * 0.3F - (float)this.img.getWidth() / 2.0F;// 25
        this.y = AbstractDungeon.floorY + 100.0F * Settings.scale - (float)this.img.getWidth() / 2.0F;// 26
        this.vX = 100.0F * Settings.scale;// 27
        this.stallTimer = 1;// 28
        this.endStall = 0.5F;
        this.scale = 1.2F * Settings.scale;// 29
        //this.rotation = MathUtils.random(-5.0F, 1.0F);// 30
    }

    public float getStallTimer(){
        return stallTimer;
    }

    public float getEndStall(){
        return endStall;
    }


    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void render(SpriteBatch sb, float x, float y) {
        super.render(sb, x, y);
    }

    @Override
    public void dispose() {

    }
}
