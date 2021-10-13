package PrismaMod.monsters;

import basemod.abstracts.CustomMonster;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.Gdx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static PrismaMod.DefaultMod.enablePlaceholder;


public abstract class AbstractPrismaMonster extends CustomMonster {

    public PrismaAnimation animHandler;

    public AbstractPrismaMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl){
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
    }

    public void setAnimation(SpriterAnimation anim) {
        if(!enablePlaceholder) {
            this.animation = anim;
        }
    }

    public class PrismaAnimation{
        public SpriterAnimation anim;
        public float duration;
        public float durationTable[];
        public final Logger logger = LogManager.getLogger(PrismaAnimation.class.getName());
        public AbstractPrismaMonster owner;
        public SpriterAnimation[] animationArrayList;
        public PrismaAnimation(SpriterAnimation[] animationArrayList, float[] durationTable, AbstractPrismaMonster owner){
            this.animationArrayList = animationArrayList;
            this.durationTable = durationTable;
            this.owner = owner;
        }
        public void update(){
            duration -= Gdx.graphics.getDeltaTime();
            if (duration <= 0){
                resetAnimation();
            }
        }
        public SpriterAnimation getAnimation(MonsterAnimationEnums animEnum){
            switch(animEnum){
                case DAMAGED:
                    duration = durationTable[1];
                    return animationArrayList[0];
                case BIG_DAMAGED:
                    duration = durationTable[1];
                    return animationArrayList[1];
                case ATTACK:
                    duration = durationTable[1];
                    return animationArrayList[2];
                case BIG_ATTACK:
                    duration = durationTable[1];
                    return animationArrayList[3];
                case CHANNEL:
                    duration = durationTable[1];
                    return animationArrayList[4];
                case BIG_CHANNEL:
                    duration = durationTable[1];
                    return animationArrayList[5];
                default:
                    duration = durationTable[0];
                    return animationArrayList[6];
            }
        }  //Basically a big switch case statement
        public void resetAnimation(){
            setAnimation(MonsterAnimationEnums.IDLE);
        }
        public void setAnimation(MonsterAnimationEnums animEnum){
            this.anim = getAnimation(animEnum);
            resetTimers();
            this.owner.setAnimation(this.anim);
        }  //this.anim = getAnimation(); resetTimers(); (AbstractPrismaMonster) ...monster.setAnimation(this.anim);
        public void resetTimers(){
            this.anim.myPlayer.setPlayers(this.anim.myPlayer.getFirstPlayer().setTime(0), this.anim.myPlayer.getSecondPlayer().setTime(0));
        }
    }

    public enum MonsterAnimationEnums {
        DAMAGED, BIG_DAMAGED, ATTACK, BIG_ATTACK, CHANNEL, BIG_CHANNEL, IDLE, IDLE_2
    }




    @Override
    public void die() {     //EVENTUALLY PUT A DEATH ANIMATION BUT IDK FOR NOW
        super.die();
    }

    //DUMB THINGS THAT NEED TO ALL BE NULL SO THAT THERE AREN'T ANY WEIRD SHAKY SHAKES
    @Override
    public void useFastShakeAnimation(float duration) {
        return;
    }

    @Override
    public void useShakeAnimation(float duration) {
        return;
    }

    @Override
    public void useFastAttackAnimation() {
        return;
    }

    @Override
    public void useSlowAttackAnimation() {
        return;
    }
}
