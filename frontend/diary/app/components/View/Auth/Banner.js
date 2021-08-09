import style from './css/banner.module.css'

const Banner = () => {
    return (
        <div className={style.auth_banner + " auth_banner_container"}>
            <div className={style.main_auth_banner}>
                <span className={style.auth_company_title}>Присоединяйся к нам!</span>
                <span className={style.auth_banner_description}>
                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod
                </span>
            </div>
            <div className={style.auth_banner_waves}>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="#0099ff" fillOpacity="1" d="M0,32L48,53.3C96,75,192,117,288,149.3C384,181,480,203,576,186.7C672,171,768,117,864,90.7C960,64,1056,64,1152,58.7C1248,53,1344,43,1392,37.3L1440,32L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>
            </div>
        </div>
    )
}

export default Banner;