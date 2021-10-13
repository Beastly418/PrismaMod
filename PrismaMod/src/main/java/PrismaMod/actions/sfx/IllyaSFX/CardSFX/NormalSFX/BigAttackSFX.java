package PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.DefaultMod.getModID;
import static PrismaMod.DefaultMod.makeSFXPath;

public class BigAttackSFX extends AbstractSFX {
    private Texture img;

    private Texture[] images;

    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private AbstractPlayer p;

    private AbstractCreature target;

    public BigAttackSFX(AbstractPlayer p, AbstractCreature target){
        this.images = new Texture[4];
        this.img = this.images[0] = TextureLoader.getTexture(makeSFXPath("Blast_Background.png"));
        this.images[1] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        this.images[2] = TextureLoader.getTexture(makeSFXPath("Rectangle.png"));
        this.color = new Color(0.5F, 0.1F, 0.5F, 0.0F);// 24
        this.scale = .2F * Settings.scale;// 29
        this.target = target;
        this.p = p;
        this.rotation = MathUtils.random(-1.0F, 1.0F);// 30
        if(Settings.FAST_MODE){
            this.fadeInTimer = this.fadeInTimer/2;
            this.fadeOutTimer = this.fadeOutTimer/2;
            this.stallTimer = this.stallTimer/2;
            this.endStall = this.stallTimer/2;
        }
        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
        CardCrawlGame.sound.play(getModID() + "SE_1");
    }

    @Override
    public void update() {
        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
            if (this.stallTimer < endStall && !playedVoice){
                double rand = Math.random()*10;
                if (rand < 3) {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_1");
                } else if (rand >= 3 && rand < 5) {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_2");
                } else {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_3");
                }
                playedVoice = true;
            }
        } else {
            //Fade in and fade out
            if(!playedSound) {
                CardCrawlGame.sound.play(getModID() + "SE_4");
                playedSound = true;
            }
            if (this.fadeInTimer != 0.0F) {// 57
                this.fadeInTimer -= Gdx.graphics.getDeltaTime();// 58
                if (this.fadeInTimer < 0.0F) {// 59
                    this.fadeInTimer = 0.0F;// 60
                }
                scale = scale + 0.04F;
                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }
                scale = scale - 0.02F;
                if (scale < .2F * Settings.scale){
                    scale = .2F * Settings.scale;
                }
                this.color.a = Interpolation.pow2.apply(0.0F, 1.0F, this.fadeOutTimer / 0.4F);// 68
            } else {
                this.isDone = true;// 70
            }

        }
    }

    public float getStallTimer(){
        return stallTimer;
    }

    public float getEndStall(){
        return endStall;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        this.img = images[0];
        //this.rotation = (float)Math.tan((p.hb_h-target.hb_h)/(p.hb_w - target.hb_x));
        //Main Background
        sb.draw(img, AbstractDungeon.player.hb_w + 2*p.hb_w, AbstractDungeon.floorY*1.5F, 0, 0, img.getWidth(), img.getHeight(), Settings.WIDTH/img.getWidth(), scale, rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);

        //Inside the beam
        if (this.fadeInTimer <= 0) {
            for (int i = 0; i < 10; i++) {
                float rand = (float) Math.random();
                float rand2 = (float) Math.random()*.2F;
                sb.setColor(new Color(rand, rand2, rand, 1));
                sb.draw(images[2], AbstractDungeon.player.hb_w + 2 * p.hb_w + (float) (1000 * Math.random()), AbstractDungeon.floorY * 1.5F + (float) (20 * Math.random()), 0, 0, img.getWidth(), img.getHeight(), (float) (scale/2 * Math.random()), (float) (scale/2 * Math.random()), rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
