package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.powers.AbstractInstallPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static PrismaMod.DefaultMod.INSTALL_BLANK;

public abstract class AbstractInstallCard extends AbstractDynamicCard {

    protected IllyaInstallObj install; //This is an install object.  This will store all of the information

    public AbstractInstallCard(String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target,
                               IllyaInstallObj install){
        super(id, INSTALL_BLANK, 2, type, color, rarity, target);
        this.install = install;
        //this.upgraded = true;   //Prevent upgrdes from happening to this card tbh
        this.exhaustOnUseOnce = true;

        String[] imgSplit = img.split("\\.");
        String img2 = imgSplit[0] + "_p.png";

        this.setBackgroundTexture(img, img2);
        //ImageMaster.CARD_FRAME_POWER_RARE_L = new TextureAtlas.AtlasRegion(new Texture(INSTALL_BLANK), 0, 0, 1, 1);
        //ImageMaster.CARD_FRAME_POWER_RARE = new TextureAtlas.AtlasRegion(new Texture(INSTALL_BLANK), 0, 0, 1, 1);
        //this.TEXT[2] = "";
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public abstract IllyaInstallObj getInstall();

    public void removeOtherInstall(AbstractPlayer p){
        for (int i = 0; i < p.powers.size(); i++) {
            if (AbstractInstallPower.class.isAssignableFrom(p.powers.get(i).getClass())) {
                if (this instanceof IllyaInstallAlterEgo || this instanceof IllyaInstallCaster || this instanceof IllyaInstallForeigner || this instanceof IllyaInstallMoonCancer) {
                    p.decreaseMaxOrbSlots(1);
                    break;
                }
            }
        }
        for (int i = 0; i < p.powers.size(); i++) {
            if (AbstractInstallPower.class.isAssignableFrom(p.powers.get(i).getClass())) {
                p.powers.remove(i);
            }
        }
    }

}
