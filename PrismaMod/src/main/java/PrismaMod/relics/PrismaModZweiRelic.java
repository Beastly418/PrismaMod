package PrismaMod.relics;

import PrismaMod.DefaultMod;
import PrismaMod.orbs.IllyaAttackOrb;
import PrismaMod.orbs.IllyaDefendOrb;
import PrismaMod.orbs.IllyaPoisonOrb;
import PrismaMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.DefaultMod.makeRelicOutlinePath;
import static PrismaMod.DefaultMod.makeRelicPath;

public class PrismaModZweiRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PrismaZwei");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Zwei_Stick.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    private static final int DRAW_AMOUNT = 2;

    public PrismaModZweiRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);


    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void atTurnStart() {
        double rand = Math.random();
        if (rand < .33) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaAttackOrb()));
        } else if (rand >= .33 && rand < .66) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaDefendOrb()));
        } else {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaPoisonOrb()));
        }
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(PrismaModRubyRelic.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(PrismaModRubyRelic.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        super.updateDescription(c);
    }


    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(PrismaModRubyRelic.ID);// 36
    }
}
