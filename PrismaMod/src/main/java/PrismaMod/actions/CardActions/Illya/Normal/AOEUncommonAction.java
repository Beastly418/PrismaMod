package PrismaMod.actions.CardActions.Illya.Normal;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.BigAttackSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.WeakPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_TWO;

public class AOEUncommonAction extends AbstractCardAction {

    public int damage;
    public float delay;
    public DamageInfo.DamageType damageType;
    public boolean hit;
    public int numWeak;
    public AOEUncommonAction(AbstractPlayer p, int damage, DamageInfo.DamageType damageType, int numWeak){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new BigAttackSFX(p, target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.damage = damage;
        this.damageType = damageType;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.numWeak = numWeak;
        //handler.setAnimtion(SWIPE_ATTACK);
        handler.setAnimation(ATTACK_TWO);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            MonsterGroup enemies = AbstractDungeon.getMonsters();
            for (AbstractMonster mon : enemies.monsters) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new WeakPower(mon, this.numWeak, true)));
                mon.damage(new DamageInfo(p, damage, damageType));
            }
            this.hit = true;
        }
        //logger.info("TICK");
        this.tickDuration();
    }
}
