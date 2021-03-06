package com.reeflog.reeflogapi.beans.animals.fishes;

import com.reeflog.reeflogapi.beans.animals.Animal;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Fish extends Animal {

    private FishSpecies fishSpecies;
    private Sexe sex;
    public enum Sexe {MALE, FEMALE, UNDEFINED}
    public enum FishSpecies {
        APOLEMICHTHYS, CENTROPYGE, CHAETODONTOPLUS, GENICANTHUS, POMACANTHUS, PYGOPLITES,
        ANTENNARIUS, HISTRIO,
        NEMANTHIAS, ODONTANTHIAS, PSEUDANTHIAS,
        APOGON, PTERAPOGON, SPHAERAMIA,
        BALISTOIDES, PSEUDOBALISTES, MELICHTHYS, ODONUS, RHINECANTHUS, SUFFLAMEN, XANTHICHTHYS, OSTRACION, LACTORIA, TETROSOMUS,
        AROTHRON, CANTHIGASTER,
        ESCENIUS, SALARIAS, ENCHELYURUS, EXALLIAS, HIRCULOPS, ISTIBLENNIUS, MEIACANTHUS, PHOLIDICHTHYS, PLAGIOTREMUS,
        PLATAX,
        ACANTHURUS, PARACANTHURUS, ZEBRASOMA,
        AMPHIPRION, PREMNAS,
        PLESIOPS,
        CHROMIS, CHRYSIPTERA, DASCYLLUS, ABUDEFDUF, AMBLYGLYPHIDODON, NEOGLYPHIDODON, PLECTROGLYPHIDODON, POMACENTRUS,
        SYNCHIROPUS,
        CIRRHITICHTHYS, OXYCIRRHITES, CIRRHITUS, PARACIRRHITES, NEOCIRRHITES,
        NEMATELEOTRIS, PTERELEOTRIS,
        AMBLYELEOTRIS, AMBLYGOBIUS, CALLOGOBIUS, YONGEICHTHYS, VALENCIENNEA, TRYSSOGOBIUS, STONOGOBIOPS, SIGNIGOBIUS, TRIMMA,
        GRAMMA,
        ZANCLUS,
        ANAMPSES, BODIANUS, CHEILINUS, CHOERODON, CIRRHILABRUS, CORIS, DIPROCTACANTHUS, GOMPHOSUS, HALICHOERES, HEMIGYMNUS, LABROIDES, LABROPSIS, MACROPHARYNGODON, NOVACULICHTHYS, PSEUDOCHEILINOPS, PSEUDODAX, PSEUDOCHEILINUS, THALASSOMA,
        SIGANUS,
        ACREICHTHYS, ALUTERUS, CHAETODERMIS, OXYMONACANTHUS,
        MONODACTYLUS,
        ECHIDNA, GYMNOTHORAX, PSEUDECHIDNA, RHINOMURAENA,
        OPISTOGNATHUS,
        CHAETODON, CHELMON, FORCIPIGER, HEMITAURICHTHYS, HENIOCHUS, PARACHAETODON,
        CETOSCARUS, SCARUS,
        DIODON,
        PSEUDOCHROMIS, PICTICHROMIS, LABRACINUS, OTHER

    }

}
