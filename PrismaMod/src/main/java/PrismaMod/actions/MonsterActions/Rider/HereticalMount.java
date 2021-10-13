package PrismaMod.actions.MonsterActions.Rider;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Rider.sfx.HereticalMountSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Rider;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class HereticalMount extends AbstractMonsterAction {

    public DamageInfo attack;
    public int blockGain;
    public int strengthGain;
    public HereticalMount(Rider m, AbstractCreature target, DamageInfo attack, int blockGain, int strengthGain){
        this.m = m;
        this.target = target;
        this.handler = getHandler(m);
        this.sfx = new HereticalMountSFX(m, (AbstractPlayer)this.target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.attack = attack;
        this.blockGain = blockGain;
        this.strengthGain = strengthGain;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.BIG_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            this.target.damage(attack);
            this.m.addBlock(blockGain);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, this.strengthGain)));
            this.hit = true;
        }
        this.tickDuration();
    }
}
