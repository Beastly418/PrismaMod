package PrismaMod.relics;

import PrismaMod.DefaultMod;
import PrismaMod.orbs.*;
import PrismaMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.DefaultMod.makeRelicOutlinePath;
import static PrismaMod.DefaultMod.makeRelicPath;

public class PrismaModSaphireRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PrismaSapphire");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Sapphire_Relic.png"));

    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    private static final int DRAW_AMOUNT = 2;

    public PrismaModSaphireRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void atBattleStart() {
        double rand = Math.random();
        if (rand < .25) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new MiyuDefendOrb()));
        } else if (rand >= .25 && rand < .5) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new MiyuAttackOrb()));
        } else if (rand >=.5 && rand < .75){
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new MiyuVulOrb()));
        } else {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new MiyuWeakOrb()));
        }
    }
}
