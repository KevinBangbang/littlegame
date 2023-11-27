package AdventureModel;

import java.util.List;

public class Mage extends BasicMonster {

    public Mage(int health, int attack, int defense, List<SpecialAbility> specialAbilities) {
        super(health, attack, defense, specialAbilities);
    }

    @Override
    public void interactWithPlayer(Player player) {
        // Specific interaction logic for Mage
    }

    // Mage-specific methods...
}
