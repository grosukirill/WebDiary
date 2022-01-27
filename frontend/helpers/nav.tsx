import { BiHomeAlt, BiBookAlt, BiSpreadsheet, BiNews, BiBookmark } from 'react-icons/bi';

export const getMenyByLink = (link: string) => {
    switch (link) {
        case "/feed": case "/": case "/new": case "/recommendations": case "/favorites":
            return [
                {
                    link: "/feed",
                    title: "Лента",
                    icon: BiNews
                },
                {
                    link: "/",
                    title: "Главная",
                    icon: BiHomeAlt
                },
                {
                    link: "/new",
                    title: "Новое",
                    icon: BiBookAlt
                },
                {
                    link: "/recommendations",
                    title: "Рекомендации",
                    icon: BiSpreadsheet
                },
                {
                    link: "/favorites",
                    title: "Избранное",
                    icon: BiBookmark
                }
            ]
        default:
            return []
    }
}