package PrismaMod.effects;

import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static PrismaMod.DefaultMod.makeOrbPath;

public class MiyuAttackOrbPassiveEffect extends AbstractPrismaEffect {
    public MiyuAttackOrbPassiveEffect(float x, float y){
        this.imgs[0] = TextureLoader.getTexture(makeOrbPath("IronCross.png"));
        //this.imgs[1] = TextureLoader.getTexture(makeOrbPath("Circle.png"));
        //this.imgs[2] = TextureLoader.getTexture(makeOrbPath("Square.png"));
        this.effectDuration = 1.0F;// 19
        this.duration = this.effectDuration;// 20
        this.startingDuration = this.effectDuration;// 21
        this.x = x + MathUtils.random(-30.0F, 30.0F) * Settings.scale;// 23
        this.y = y + MathUtils.random(-30.0F, 30.0F) * Settings.scale;// 24
        this.sX = this.x;// 25
        this.sY = this.y;// 26
        this.tX = x;// 27
        this.tY = y;// 28
        int tmp = MathUtils.random(2);// 30
        if (tmp == 0) {// 31
            this.color = Settings.LIGHT_YELLOW_COLOR.cpy();// 32
        } else if (tmp == 1) {// 33
            this.color = Color.CYAN.cpy();// 34
        } else {
            this.color = Color.SALMON.cpy();// 36
        }

        this.scale = MathUtils.random(0.3F, 1.2F) * Settings.scale;// 39
        this.renderBehind = true;// 40
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);// 52
        sb.setBlendFunction(770, 1);// 53
        sb.draw(this.imgs[0], this.x, this.y, (float)this.imgs[0].getWidth() / 2.0F, (float)this.imgs[0].getHeight() / 2.0F, (float)this.imgs[0].getWidth(), (float)this.imgs[0].getHeight(), this.scale * MathUtils.random(0.7F, 1.4F), this.scale * MathUtils.random(0.7F, 1.4F));
        sb.setBlendFunction(770, 771);// 65
    }

    @Override
    public void dispose() {

    }
}
