package PrismaMod.actions.MonsterActions.SaberAlter;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.SaberAlter.sfx.BasicAttackSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.SaberAlter;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BasicAttack extends AbstractMonsterAction {

    public int damage;
    public DamageInfo.DamageType damageType;
    public DamageInfo attack;
    public int numAttacks;
    public BasicAttack(SaberAlter m, AbstractPlayer target, DamageInfo attack, int numAttacks){
        this.m = m;
        this.handler = getHandler(m);
        this.sfx = new BasicAttackSFX(m, target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.damage = attack.output;
        this.damageType = attack.type;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.attack = attack;
        this.numAttacks = numAttacks;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.ATTACK);

    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            for (int i = 0; i < numAttacks; i++)
                target.damage(attack);
            this.hit = true;
        }
        this.tickDuration();
    }
}
