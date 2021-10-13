package PrismaMod.actions.MonsterActions.SaberAlter;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.SaberAlter.sfx.ExcaliburMorganSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.SaberAlter;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExcaliburMorgan extends AbstractMonsterAction {

    public int damage;
    public DamageInfo.DamageType damageType;
    public DamageInfo attack;
    public ExcaliburMorgan(SaberAlter m, AbstractPlayer target, DamageInfo attack){
        this.m = m;
        this.handler = getHandler(m);
        this.sfx = new ExcaliburMorganSFX(m, target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.damage = attack.output;
        this.damageType = attack.type;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.attack = attack;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.BIG_ATTACK);

    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            for(int i = 0; i < 5; i++)  //Hit 5 times
                target.damage(attack);
                //AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(m, damage, damageType), AttackEffect.NONE));
            this.hit = true;
        }
        this.tickDuration();
    }
}
