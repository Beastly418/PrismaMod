package PrismaMod.actions.CardActions.Illya.Normal;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import PrismaMod.powers.PartialInstallAssassinPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_TWO;

public class PartialAssassinAction extends AbstractCardAction {

    //private static final Logger logger = LogManager.getLogger(CommonAttackAction.class.getName());
    public int damage;
    public float delay;
    public DamageInfo.DamageType damageType;
    public boolean hit;
    public PartialAssassinAction(AbstractPlayer p, int damage){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.damage = damage;
        this.hit = false;
        handler.setAnimation(SKILL_TWO);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PartialInstallAssassinPower(p, p, 1, damage)));
            this.hit = true;
        }
        //logger.info("TICK");
        this.tickDuration();
    }
}
