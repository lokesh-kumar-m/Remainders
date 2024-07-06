
import { useNavigate,Link } from "react-router-dom";
import { useAuth } from "../auth";
import "./header.css"
const Header= () =>{
    const authContext=useAuth()
    const navigateTo=useNavigate()

    function handlelogout(){
        authContext.islogout()
        navigateTo(`/auth/login`)
    }
    function handleloggingin(){
      navigateTo(`/auth/login`)
    }

    return(
        <header className="header">
        <nav className="nav container">
          <Link to={`/home/${authContext.username}`} className="nav-logo">TODO</Link>
          
          <div className="nav-menu">
            <ul className="nav-list">
              <li className="nav-item">
                <Link to={`/home/${authContext.username}`} className='nav-link active-link'>{authContext.username}</Link>
              </li>
                
              <li className="nav-item">
                {authContext.isAuthentic? 
                <button className="btn"onClick={handlelogout}>Logout</button>:
                <button className="btn"onClick={handleloggingin}>Login</button>
                }
              </li>
            </ul>
          </div>
          
        </nav>
      </header>
    )
}
export default Header;