import { DropdownMenu, DropdownMenuTrigger, DropdownMenuContent, DropdownMenuItem } from "@/components/ui/dropdown-menu";
import logo from "../assets/carwarsbeztla.png";
import "./navbar.css"; // Importujemy styl CSS

// Funkcja komponentu Navbar
const Navbar = () => {
    return (
        <nav className="flex justify-between items-center px-8 py-4 bg-white border-b border-gray-200 dark:bg-gray-900 dark:border-gray-700">
            {/* Logo */}
            <div className="flex items-center space-x-3">
                <img src={logo} alt="Car Wars Logo" className="h-10 w-auto" />
                <span className="text-lg font-extrabold text-gray-900 dark:text-white">
                    Car<span className="text-yellow-500">Wars</span>
                </span>
            </div>

            {/* Linki menu (dla większych ekranów) */}
            <div className="hidden md:flex items-center space-x-8">
                <a href="/shop" className="navbar-link text-sm font-medium">
                    Shop
                </a>
                <a href="/race" className="navbar-link text-sm font-medium">
                    Race
                </a>
                <a href="/main" className="navbar-link text-sm font-medium">
                    Explore
                </a>
                <a href="/contact" className="navbar-link text-sm font-medium">
                    Contact
                </a>
                <a href="/account" className="navbar-link text-sm font-medium">
                    Account
                </a>
            </div>

            {/* Menu Dropdown (dla mobilnych ekranów) */}
            <div className="md:hidden">
                <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                        {/* Ikona menu */}
                        <button className="text-gray-700 dark:text-gray-200 p-2 rounded transition-colors">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                                strokeWidth="1.5"
                                stroke="currentColor"
                                className="w-6 h-6"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    d="M3.75 5.25h16.5M3.75 12h16.5M3.75 18.75h16.5"
                                />
                            </svg>
                        </button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end" className="w-44 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-md shadow-lg">
                        <DropdownMenuItem>
                            <a href="#" className="navbar-link">
                                Shop
                            </a>
                        </DropdownMenuItem>
                        <DropdownMenuItem>
                            <a href="#" className="navbar-link">
                                Race
                            </a>
                        </DropdownMenuItem>
                        <DropdownMenuItem>
                            <a href="#" className="navbar-link">
                                Explore
                            </a>
                        </DropdownMenuItem>
                        <DropdownMenuItem>
                            <a href="#" className="navbar-link">
                                Contact
                            </a>
                        </DropdownMenuItem>
                        <DropdownMenuItem>
                            <a href="#" className="navbar-link">
                                Account
                            </a>
                        </DropdownMenuItem>
                    </DropdownMenuContent>
                </DropdownMenu>
            </div>
        </nav>
    );
};

export default Navbar;
