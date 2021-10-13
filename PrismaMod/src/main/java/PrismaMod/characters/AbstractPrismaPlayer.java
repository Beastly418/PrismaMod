package PrismaMod.characters;

import PrismaMod.characters.Animation.AbstractPrismaPlayerAnim;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static PrismaMod.DefaultMod.enablePlaceholder;

public abstract class AbstractPrismaPlayer extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(AbstractPrismaPlayer.class.getName());
    int spriteWidth = 8;//1400/5;
    int spriteHeight = 8;//1524/6;
    float fps = 1/3;
    boolean loop = false;

    public ArrayList<AbstractPrismaPlayerAnim> animations = new ArrayList<>(10);
    public String[] animationPaths = new String[10];

    public AbstractPrismaPlayer(String name, PlayerClass setClass, String[] orbTextures, String orbVFX, float[] layerSpeeds, SpriterAnimation idle){
        super(name, setClass, orbTextures, orbVFX, layerSpeeds, idle);
    }

    public void setAnimation(SpriterAnimation anim){
        if(!enablePlaceholder) {
            this.animation = anim;
        }
    }

    public abstract class AbstractPrismaPlayerHandler {
        public ArrayList<AbstractPrismaPlayerAnim> animationList;
        public AbstractPrismaPlayer owner;
        public AbstractPrismaPlayerAnim anim;
        //private SpriteBatch sb;

        public AbstractPrismaPlayerHandler(AbstractPrismaPlayer owner, ArrayList<AbstractPrismaPlayerAnim> animationList) {
            //sb = new SpriteBatch();
            this.owner = owner;
            this.animationList = animationList;
            this.setAnimation(PlayerAnimationEnums.IDLE);
        }

        public void update() {
            anim.update(Gdx.graphics.getDeltaTime());
            if(anim.isDone) {
                resetAnimation();
            }
        }

        public void render(SpriteBatch sb) {
            anim.render(sb);
        }

        public void dispose() {
            for(AbstractPrismaPlayerAnim a : animationList) {
                a.dispose();
            }
        }

        public void resetAnimation(){
            this.anim.setFrame(0);
            setAnimation(PlayerAnimationEnums.IDLE);
        }

        public void setAnimation(PlayerAnimationEnums animation) {
            this.anim = getAnimation(animation);
            logger.info("set animation:" + animation);
        }

        public abstract AbstractPrismaPlayerAnim getAnimation(PlayerAnimationEnums animation);
    }

    public enum PlayerAnimationEnums{
        ATTACK_ONE, ATTACK_TWO, SKILL_ONE, SKILL_TWO, SKILL_THREE, DAMAGED, BIG_DAMAGED, IDLE
    }
}
