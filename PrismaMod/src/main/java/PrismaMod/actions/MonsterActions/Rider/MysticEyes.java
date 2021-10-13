package PrismaMod.actions.MonsterActions.Rider;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Rider.sfx.MysticEyesSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Rider;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class MysticEyes extends AbstractMonsterAction {

    public int frail;
    public int vul;
    public int weak;
    public MysticEyes(Rider m, AbstractCreature target, int frail, int vul, int weak){
        this.m = m;
        this.target = target;
        this.handler = getHandler(m);
        this.sfx = new MysticEyesSFX((AbstractPlayer)this.target);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.frail = frail;
        this.vul = vul;
        this.weak = weak;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.CHANNEL);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            this.target.addPower(new FrailPower(this.target, this.frail, false));
            this.target.addPower(new VulnerablePower(this.target, this.vul, false));
            this.target.addPower(new WeakPower(this.target, this.weak, false));
            this.hit = true;
        }
        this.tickDuration();
    }
}
