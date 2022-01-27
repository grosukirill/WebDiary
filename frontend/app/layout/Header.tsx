import { connect } from 'react-redux'
import { BiNews, BiCalendar, BiCategoryAlt, BiGroup } from 'react-icons/bi';
import { CgProfile } from 'react-icons/cg';
import { MdSubscriptions } from 'react-icons/md';

interface IHeaderProps {
    denyPaths: boolean
    auth: IauthReducer
    children: React.ReactNode
}

const Header: React.FC<IHeaderProps> = ({ denyPaths, ...props }) => {

    const user = props.auth.user;
    if (!denyPaths) {
        return (
            <div className="header_tab">

                    <div className="header_tab_container">
                        <div className="header_tab_block">
                            <CgProfile size={24} />
                        </div>
                        <div className="header_tab_block active_header_tab_block">
                            <BiNews size={24} />
                        </div>
                        <div className="header_tab_block">
                            <BiCalendar size={24} />
                        </div>
                        <div className="header_tab_block">
                            <BiCategoryAlt size={24} />
                        </div>
                        <div className="header_tab_block">
                            <MdSubscriptions size={24} />
                        </div>
                        <div className="header_tab_block">
                            <BiGroup size={24} />
                        </div>
                    </div>

                    {props.children}
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

export default connect(mapStateToProps)(Header);