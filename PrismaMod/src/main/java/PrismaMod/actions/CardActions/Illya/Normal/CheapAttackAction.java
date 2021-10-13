package PrismaMod.actions.CardActions.Illya.Normal;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.BigAttackSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_ONE;

public class CheapAttackAction extends AbstractCardAction {

    public int damage;
    public float delay;
    public DamageInfo.DamageType damageType;
    public boolean hit;
    public int weakAmt;

    public CheapAttackAction(AbstractPlayer p, AbstractCreature target, int damage, DamageInfo.DamageType damageType, int weakAmt) {
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new BigAttackSFX(p, target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.damage = damage;
        this.damageType = damageType;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.weakAmt = weakAmt;
        //handler.setAnimtion(BLAST_ATTACK);
        handler.setAnimation(ATTACK_ONE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if (this.delay <= 0 && !this.hit) {
            //logger.info("HIT");
            this.target.damage(new DamageInfo(this.p, this.damage, this.damageType));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.target, new VulnerablePower(this.target, this.weakAmt, false)));
            this.hit = true;
        }
        //logger.info("TICK");
        this.tickDuration();
    }
}
