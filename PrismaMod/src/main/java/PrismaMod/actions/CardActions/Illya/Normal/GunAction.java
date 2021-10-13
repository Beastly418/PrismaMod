package PrismaMod.actions.CardActions.Illya.Normal;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.AttackSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_ONE;

public class GunAction extends AbstractCardAction {

    //private static final Logger logger = LogManager.getLogger(CommonAttackAction.class.getName());
    public int damage;
    public float delay;
    public DamageInfo.DamageType damageType;
    public boolean hit;
    public GunAction(AbstractPlayer p, AbstractCreature target, int damage, DamageInfo.DamageType damageType){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new AttackSFX(p, target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.damage = damage;
        this.damageType = damageType;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        handler.setAnimation(ATTACK_ONE);
        //handler.setAnimtion(BLAST_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            //logger.info("HIT");
            target.damage(new DamageInfo(p, damage, damageType));
            this.hit = true;
        }
        //logger.info("TICK");
        this.tickDuration();
    }
}
