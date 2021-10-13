package PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX;

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

public class BrideSFX extends AbstractSFX {
    private Texture img;// = TextureLoader.getTexture(makeOrbPath("Testament_orb.png"));
    private float stallTimer = 0;

    private Texture[] images;

    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private AbstractPlayer p;

    private AbstractCreature target;

    public boolean playedSound = false;
    public boolean playedVoice = false;
    public float difY;

    private class gift {
        public Texture image;
        public float x;
        public float y;
        public Color color;
        public gift(Texture image, float x, float y, Color color){
            this.image = image;
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    public gift[] pictures = new gift[25];

    public BrideSFX(AbstractCreature target){
        this.images = new Texture[4];
        this.img = this.images[0] = TextureLoader.getTexture(makeSFXPath("gift.png"));
        this.p = (AbstractPlayer)target;
        for(int i = 0; i < pictures.length; i++){
            pictures[i] = new gift(images[0], AbstractDungeon.player.hb_w + (float)(Math.random()*2*p.hb_w), 150 + AbstractDungeon.floorY * 1.5F + (float)(Math.random()*250), new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1));
        }
        this.color = new Color(0.5F, 0.1F, 0.5F, 0.0F);// 24
        this.scale = .5F * Settings.scale;// 29
        this.target = target;
        this.difY = 0;//AbstractDungeon.floorY * 1.5F + 100;
        this.rotation = MathUtils.random(-1.0F, 1.0F);// 30
        if(Settings.FAST_MODE){
            this.fadeInTimer = this.fadeInTimer/2;
            this.fadeOutTimer = this.fadeOutTimer/2;
            this.stallTimer = this.stallTimer/2;
            this.endStall = this.stallTimer/2;
        }
        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
        this.stallTimer = .1F;
        CardCrawlGame.sound.play(getModID() + "SE_1");
    }

    @Override
    public void update() {
        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
            if (this.stallTimer < endStall && !playedVoice){
                double rand = Math.random()*10;
                if (rand < 7) {
                    CardCrawlGame.sound.play(getModID() + "SKILL_1");
                } else {
                    CardCrawlGame.sound.play(getModID() + "SKILL_2");
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
                for(gift g: pictures){
                    g.y = g.y - difY;
                    g.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);
                }
                difY += 1;
                //this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }
                for(gift g: pictures){
                    g.y = g.y - difY;
                    g.color.a = Interpolation.pow2.apply(0.0F, 1.0F, this.fadeOutTimer / 0.4F);
                }
                difY += 1;
                //this.color.a = Interpolation.pow2.apply(0.0F, 1.0F, this.fadeOutTimer / 0.4F);// 68
            } else {
                this.isDone = true;// 70
            }

        }
    }

    @Override
    public void render(SpriteBatch sb) {
        for(gift g: pictures){
            sb.setColor(g.color);
            sb.draw(g.image, g.x, g.y, 0, 0, g.image.getWidth(), g.image.getHeight(), scale, scale, rotation, 0, 0, g.image.getWidth(), g.image.getHeight(), false, false);
        }
    }

    public float getStallTimer(){
        return stallTimer;
    }

    public float getEndStall(){
        return endStall;
    }

    @Override
    public void dispose() {

    }
}
