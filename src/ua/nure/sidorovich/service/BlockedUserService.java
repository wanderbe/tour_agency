package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.BlockedUser;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.util.DataBaseException;

public interface BlockedUserService {

	BlockedUser getByUserID(long userId) throws DataBaseException;

	long save(BlockedUser blockedUser) throws DataBaseException;

	BlockedUser getByUser(User user) throws DataBaseException;

	List<BlockedUser> getList() throws DataBaseException;

	boolean remove(BlockedUser blockedUser) throws DataBaseException;

}