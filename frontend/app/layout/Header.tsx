import Image from 'next/image'
import NotificationSvg from '../components/svg/Header/Notification'
import AddCircleSvg from '../components/svg/Header/AddCircle'
import SearchSvg from '../components/svg/Header/Search'
import DropDown from '../components/Main/DropDown/Dropdown'
import SettingsSvg from '../components/svg/Header/Settings'
import StatisticsSvg from '../components/svg/Header/Statistics'
import LogoutSvg from '../components/svg/Header/Logout'
import { logout } from '../../store/actions/authActions'
import { connect } from 'react-redux'

interface IHeaderProps {
    denyPaths: boolean
    auth: IauthReducer
}

const Header: React.FC<IHeaderProps> = ({ denyPaths, ...props }) => {

    const select = (item: IDropDownMainData) => {
        if (item.type === "logout") {
            logout();
        }
    }

    const user = props.auth.user;
    if (!denyPaths) {
        return (
            <header>
                <div className="header_container">

                    <div className="header_left_block">
                        <div className="header_logo">
                            <span>Дневник Вопросов</span>
                        </div>
                        <div className="header_search_input">
                            <div className="header_search_icon">
                                <SearchSvg />
                            </div>
                            <input type="text" placeholder="Поиск по вопросам, пользователям, сообществам..." />
                        </div>
                    </div>

                    <div className="right_header_block">
                        <div className="header_notification_icon">
                            <AddCircleSvg />
                        </div>
                        <div className="header_notification_icon">
                            <NotificationSvg />
                        </div>
                        <div className="header_profile_block">
                            {Object.keys(user).length !== 0 ? (
                                <DropDown
                                    children={
                                        <img
                                            src={user.avatar}
                                            alt="Picture"
                                            width={37}
                                            height={37}
                                        />
                                    }
                                    dataId="header_avatar"
                                    data={
                                        [
                                            {
                                                icon: <StatisticsSvg />,
                                                name: "Статистика"
                                            },
                                            {
                                                icon: <SettingsSvg />,
                                                name: "Настройки"
                                            },
                                            {
                                                icon: <LogoutSvg />,
                                                name: "Выйти",
                                                type: "logout"
                                            }
                                        ]
                                    }
                                    select={select}
                                    containsIcons
                                />
                            ) : ""}
                        </div>
                    </div>
                </div>
            </header>
        )
    }
    return null
}

const mapStateToProps = (state: RootState) => {
    return {
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(Header);