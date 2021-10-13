package PrismaMod.actions.sfx;

import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public abstract class AbstractSFX extends AbstractGameEffect {
    //AbstractDungeon.effectsQueue.add(new HealEffect(this.hb.cX - this.animX, this.hb.cY, healAmount));
    //these values are all from the Abstract Creature class.  The X and Y coords the first 2, heal amount is the number on the screen

    public boolean playedSound = false;
    public boolean playedVoice = false;
    protected float stallTimer = 1.15F;
    protected float endStall = stallTimer/2F;

    public abstract float getStallTimer();
    public abstract float getEndStall();
}
