import ProfileSvg from '../components/svg/Sidebar/Profile'
import FeedSvg from '../components/svg/Sidebar/Feed'
import CategorySvg from '../components/svg/Sidebar/Category'
import SubscriptionsSvg from '../components/svg/Sidebar/Subscriptions'
import CommunitySvg from '../components/svg/Sidebar/Community'
import Bookmark from '../components/svg/Sidebar/Bookmark'
import CalendarSvg from '../components/svg/Sidebar/Calendar'
import RecommendationSvg from '../components/svg/Sidebar/Recommendation'
import HomeSvg from '../components/svg/Sidebar/Home'
import Link from 'next/link'
import Image from 'next/image'
import { CgSearch } from 'react-icons/cg';
import { getMenyByLink } from '../../helpers/nav'

import { BiHomeAlt, BiBookAlt, BiSpreadsheet, BiNews, BiGroup } from 'react-icons/bi';
interface ISidebarProps {
    denyPaths: boolean
    route: string
}

const Sidebar: React.FC<ISidebarProps> = ({ denyPaths, route }) => {
    if (!denyPaths) {
        const menuRoute = getMenyByLink(route)
        return (
            <div className="sidebar">
                <div className="sidebar_inside">
                    <div className="header_sidebar_container">
                        <Image
                            src="/webdiary_logo.png"
                            alt="WebDiary logo"
                            width={54}
                            height={54}
                        />
                        <div className="header_search_input">
                            <div className="header_search_icon">
                                <CgSearch
                                    size={22}
                                    color='#5f5f5f'
                                />
                            </div>
                            <input type="text" placeholder="Поиск" />
                        </div>
                    </div>
                    <div className="sidebar_component">
                    </div>

                    {menuRoute.length !== 0 ? (

                        <div className="sidebar_component">
                            <div className="sidebar_menu_block">
                                <span className="sidebar_menu_title">Меню</span>
                            </div>

                            <ul>
                                {menuRoute.map((item, index) => {
                                    return (
                                        <Link href={item.link} key={index}>
                                            <li className={item.link === route ? "active_sidebar_li" : ""}>
                                                <div className="sidebar_icon">
                                                    <item.icon size={25} />
                                                </div>
                                                <span>{item.title}</span>
                                            </li>
                                        </Link>
                                    )
                                })}
                            </ul>
                        </div>

                    ) : null}

                    <div className="sidebar_component">
                        <div className="sidebar_menu_block">
                            <span className="sidebar_menu_title">Подписки</span>
                        </div>

                        <ul>
                            <li>
                                <Image
                                    src="/4fsa.png"
                                    alt="WebDiary logo"
                                    width={32}
                                    height={32}
                                />
                                <span>Вопросы психологии</span>
                            </li>
                            <li>
                                <Image
                                    src="/2a.png"
                                    alt="WebDiary logo"
                                    width={32}
                                    height={32}
                                />
                                <span>Дневник эко</span>
                            </li>
                            <li>
                                <Image
                                    src="/1sd.svg.png"
                                    alt="WebDiary logo"
                                    width={32}
                                    height={32}
                                />
                                <span>Тема вопроса</span>
                            </li>
                            <li>
                                <Image
                                    src="/3fdsa.png"
                                    alt="WebDiary logo"
                                    width={32}
                                    height={32}
                                />
                                <span>Вопросы Джо</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        )
    }
    return null
}

export default Sidebar;