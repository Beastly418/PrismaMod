package PrismaMod.characters.Animation;

import PrismaMod.DefaultMod;
import PrismaMod.characters.AbstractPrismaPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AbstractPrismaPlayerAnim extends AbstractPrismaAnimation {
    public SpriteSheet animation;
    public TextureRegion currentFrame;
    public boolean isDone = false;

    public AbstractPrismaPlayerAnim(String animationPath, int spriteWidth, int spriteHeight) {
        this(animationPath, spriteWidth, spriteHeight, 0, 0, 1, true);
    }

    public AbstractPrismaPlayerAnim(String animationPath, int spriteWidth, int spriteHeight, float fps) {
        this(animationPath, spriteWidth, spriteHeight, 0, 0, fps, true);
    }

    public AbstractPrismaPlayerAnim(String animationPath, int spriteWidth, int spriteHeight, int xLoc, int yLoc, float fps) {
        this(animationPath, spriteWidth, spriteHeight, xLoc, yLoc, fps, true);
    }

    public AbstractPrismaPlayerAnim(String animationPath, int spriteWidth, int spriteHeight, int xLoc, int yLoc, float fps, boolean loop) {
        this(animationPath, spriteWidth, spriteHeight, xLoc, yLoc, fps, loop, 1, 1);
    }

    public AbstractPrismaPlayerAnim(String animationPath, int spriteWidth, int spriteHeight, int xLoc, int yLoc, float fps, boolean loop, float scalex, float scaley) {
        this.scalex = scalex;
        this.scaley = scaley;
        this.animation = new SpriteSheet(animationPath, spriteWidth, spriteHeight);
        this.animation.getAnim().trimToSize();
        this.currentFrame = this.animation.getAnim().get(this.frame);
        this.timer = 0;
        this.fps = fps;
        this.frame = 0;
        this.loop = loop;
        this.x = (int)(xLoc - this.animation.width/2*scalex);
        this.y = yLoc;
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if(AbstractDungeon.screen != AbstractDungeon.CurrentScreen.DEATH) {
            sb.draw(this.currentFrame, this.x, this.y, 0F, 0F, this.animation.width, this.animation.height, this.scalex, this.scaley, 0F);
        }
    }

    @Override
    public void update(float dt) {
        this.timer = this.timer + dt;
        //DefaultMod.logger.info("dt " + dt);
        //DefaultMod.logger.info("timer" + timer);
        if(!Settings.FAST_MODE) {
            if (this.timer >= this.fps) {
                //move to next frame
                this.timer = 0;
                this.frame++;
                if (this.frame > this.animation.getAnim().size() - 1 && this.loop) {
                    this.frame = 0;
                }
                if (!this.isDone)
                    this.currentFrame = this.animation.getAnim().get(this.frame);
            }
        } else {
            if (this.timer >= this.fps/5) {
                //move to next frame
                this.timer = 0;
                this.frame++;
                if (this.frame > this.animation.getAnim().size() - 1 && this.loop) {
                    this.frame = 0;
                }
                if (!this.isDone)
                    this.currentFrame = this.animation.getAnim().get(this.frame);
            }
        }
        if(frame == this.animation.getAnim().size()-1 && !this.loop) {
            this.isDone = true;
        } else {
            this.isDone = false;
        }
    }

    @Override
    public void dispose() {
        this.animation.getAnim().get(0).getTexture().dispose();
    }
}
