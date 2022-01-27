import { useEffect, useState } from 'react'
import DropDown from '../components/Main/DropDown/Dropdown'
import SettingsSvg from '../components/svg/Header/Settings'
import StatisticsSvg from '../components/svg/Header/Statistics'
import LogoutSvg from '../components/svg/Header/Logout'
import { connect } from 'react-redux'
import { logout } from '../../store/actions/authActions'
import Static from '../../services/Static'

import { BiBell, BiAddToQueue, BiStats, BiCog, BiExit } from 'react-icons/bi';

interface IHeaderProps {
    denyPaths: boolean
    auth: IauthReducer
}

const RightSidebar: React.FC<IHeaderProps> = ({ denyPaths, ...props }) => {
    const [categories, setCategory] = useState<ICategories[]>([])
    const select = (item: IDropDownMainData) => {
        if (item.type === "logout") {
            logout();
        }
    }
    const user = props.auth.user;

    useEffect(() => {
        if (user.id) {
            Static.getCategories(props.auth.token).then(res => {
                if (res.status) {
                    setCategory(res.data)
                }
            })
        }
    }, []);

    if (!denyPaths) {
        return (
            <div className="right_sidebar_container">
                <div className="right_sidebar_inside">
                    <div className="right_header_block">
                        <div className="header_notification_icon">
                            <BiAddToQueue size={24} />
                        </div>
                        <div className="header_notification_icon">
                            <BiBell size={24} />
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
                                                icon: <BiStats size={23} />,
                                                name: "Статистика"
                                            },
                                            {
                                                icon: <BiCog size={23} />,
                                                name: "Настройки"
                                            },
                                            {
                                                icon: <BiExit size={23} />,
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
                    <div className="category_sidebar_container">
                        {categories.length !== 0 ? (
                            categories.map((item, index) => {
                                return (
                                    <div className="category_sidebar_block" key={index}>
                                        <span>{item.category}</span>
                                    </div>
                                )
                            })
                        ) : null}
                    </div>
                </div>
            </div>
        )
    }
    return null
}

const mapStateToProps = (state: RootState) => {
    return {
        auth: state.authReducer
    }
}

export default connect(mapStateToProps)(RightSidebar);