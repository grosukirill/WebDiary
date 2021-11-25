import ProfileSvg from '../components/svg/Sidebar/Profile'
import FeedSvg from '../components/svg/Sidebar/Feed'
import CategorySvg from '../components/svg/Sidebar/Category'
import SubscriptionsSvg from '../components/svg/Sidebar/Subscriptions'
import CommunitySvg from '../components/svg/Sidebar/Community'
import Bookmark from '../components/svg/Sidebar/Bookmark'
import CalendarSvg from '../components/svg/Sidebar/Calendar'
import RecommendationSvg from '../components/svg/Sidebar/Recommendation'
import Link from 'next/link'

interface ISidebarProps {
    denyPaths: boolean
}

const Sidebar: React.FC<ISidebarProps> = ({ denyPaths }) => {
    if (!denyPaths) {
        return (
            <div className="sidebar">
                <ul>
                    <li>
                        <ProfileSvg />
                        <span>Профиль</span>
                    </li>
                    <Link href="/feed">
                        <li className="acive_sidebar_li">
                            <FeedSvg />
                            <span>Лента</span>
                        </li>
                    </Link>
                    <li>
                        <CalendarSvg />
                        <span>Календарь</span>
                    </li>
                    <li>
                        <CategorySvg />
                        <span>Категории</span>
                    </li>
                    <li>
                        <SubscriptionsSvg />
                        <span>Подписки</span>
                    </li>
                    <li>
                        <CommunitySvg />
                        <span>Сообщества</span>
                    </li>
                    <li>
                        <RecommendationSvg />
                        <span>Рекомендации</span>
                    </li>
                    <li>
                        <Bookmark />
                        <span>Избранное</span>
                    </li>
                </ul>

                <div className="sidebar_menu_block">
                    <span className="sidebar_menu_title">Подписки</span>
                </div>

                <ul>
                    <li>
                        <CommunitySvg />
                        <span>Вопросы психологии</span>
                    </li>
                    <li>
                        <CommunitySvg />
                        <span>Дневник эко</span>
                    </li>
                    <li>
                        <CommunitySvg />
                        <span>Тема вопроса</span>
                    </li>
                    <li>
                        <CommunitySvg />
                        <span>Вопросы Джо</span>
                    </li>
                </ul>
            </div>
        )
    }
    return null
}

export default Sidebar;