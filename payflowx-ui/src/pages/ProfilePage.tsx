import { useEffect, useState } from "react";
import getMe from "../api/user";

const ProfilePage = () => {
  const [name, setName] = useState("");

  useEffect(() => {
    const loadUser = async () => {
      const data = await getMe();
      setName(data.name);
    };
    loadUser();
  }, []);

  return (
    <div>
      <p>{name}</p>
    </div>
  );
};

export default ProfilePage;
