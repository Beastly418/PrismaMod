package PrismaMod.effects;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public abstract class AbstractPrismaEffect extends AbstractGameEffect {
    protected float effectDuration;
    protected float x;
    protected float y;
    protected float sX;
    protected float sY;
    protected float tX;
    protected float tY;
    protected Texture[] imgs;
}
