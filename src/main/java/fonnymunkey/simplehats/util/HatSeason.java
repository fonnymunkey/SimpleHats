package fonnymunkey.simplehats.util;

import java.util.Date;

public enum HatSeason {
    EASTER {
        @Override
        public boolean compareDate(int date) {
            if(easterDayMin == 0 || easterDayMax == 0) {
                int year = (new Date()).getYear();
                int a = year % 19,
                        b = year / 100,
                        c = year % 100,
                        d = b / 4,
                        e = b % 4,
                        g = (8 * b + 13) / 25,
                        h = (19 * a + b - d - g + 15) % 30,
                        j = c / 4,
                        k = c % 4,
                        m = (a + 11 * h) / 319,
                        r = (2 * e + 2 * j - k - h + m + 32) % 7,
                        n = (h - m + r + 90) / 25,
                        p = (h - m + r + n + 19) % 32;
                easterDayMin = p-7<=0 ? ((n-1)*100)+(p+24) : (n*100)+(p-7);
                easterDayMax = p+7>=31 ? ((n+1)*100)+(p-24) : (n*100)+(p+7);
            }
            return date >= easterDayMin && date <= easterDayMax;
        }
    },
    SUMMER {
        @Override
        public boolean compareDate(int date) {
            return date >= 627 && date <= 711;
        }
    },
    HALLOWEEN {
        @Override
        public boolean compareDate(int date) {
            return date >= 1017 && date <= 1031;
        }
    },
    FESTIVE {
        @Override
        public boolean compareDate(int date) {
            return date >= 1217 && date <= 1231;
        }
    },
    NONE {
        @Override
        public boolean compareDate(int date) {
            return true;
        }
    };

    private static int easterDayMin = 0;
    private static int easterDayMax = 0;
    private static HatSeason cachedSeason = HatSeason.NONE;
    private static int cachedDay = 0;

    public static HatSeason getSeason() {
        int cachedDayPre = cachedDay;
        int date = convertDate();

        if (cachedDayPre != cachedDay) {
            for (HatSeason season : HatSeason.values()) {
                if (!season.equals(HatSeason.NONE) && season.compareDate(date)) {
                    cachedSeason = season;
                    break;
                }
            }
        }
        return cachedSeason;
    }

    public abstract boolean compareDate(int date);

    private static int convertDate() {
        Date date = new Date();
        cachedDay = date.getDate();
        return ((date.getMonth()+1)*100)+date.getDate();
    }
}
