package com.dhcc.demo.module.user.controller;

import com.dhcc.demo.module.user.entity.User;
import com.dhcc.demo.module.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guogang
 * @since 2020-12-05
 */
@Api(tags = {""})
@RestController
@RequestMapping("/user/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    UserController userc;
    @Resource
    private IUserService userService;
    @ApiOperation(value = "新增user 事务提交测试(选择序号填入testType进行测试)\n " +
            " 1、propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程\n" +
            "    isolation = Isolation.READ_UNCOMMITTED   事务读未提交测试\n"+
            " 2、propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程\n" +
            "    isolation = Isolation.READ_COMMITTED   事务读已以提交测试\n"+
            " 3、propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程\n" +
            "    isolation = Isolation.REPEATABLE_READ   事务可重复读测试\n"+
            " 4、propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程\n" +
            "    isolation = Isolation.SERIALIZABLE   事务串行化测试\n"
    )
    @PostMapping()
//    @Transactional
//    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public int add(@RequestParam String testType,@RequestBody User user){
        log.info("事务读提交测试");
        long total = userService.findListByPage(1, 10).getRecords().size();
        log.info("数据库初始数据条数[{}]",total);
        //事务1

        int rows=userService.add(user);
        total = userService.findListByPage(1, 10).getRecords().size();
        log.info("事务1 数据库添加user 总条数：[{}]",total);
        //事务2
        log.info("事务2 执行添加 user");
        switch (testType){
            case "1":
                userc.add2(user);
                break;
            case "2":
                userc.add3(user);
                break;
            case "3":
                userc.add4(user);
                break;
            case "4":
                userc.add5(user);
                break;
            default:
                User user2 = userService.findById((long) 205);
                log.info("update 事务1 查询user205",user2);
                user2.setPhone("123456");
                int row = userc.update(user2);
                userc.add6(user2);
                log.info("update 事务1 更新user205,影响行数[{}]",user2,row);
        }
        total = userService.findListByPage(1, 10).getRecords().size();
        log.info("事务1 数据库总条数：[{}]",total);
        return rows;
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ)
//    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED)
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
    public User add6(User user){
        User user2 = userService.findById((long) 205);
        log.info("update 事务2 查询user205",user2);
        user2.setPhone("789654");
        int rows = userc.update(user2);
        log.info("update 事务2 更新user205,影响行数[{}]",user2,rows);
        return user2;
    }

    /**
     * propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程
     *
     * isolation = Isolation.READ_UNCOMMITTED   事务读未提交测试
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
    public int add2(User user){
        int rows=userService.add(user);
        int total = userService.findListByPage(1, 10).getRecords().size();
        log.info("事务2 数据库总条数：[{}]",total);
        return rows;
    }
    /**
     * propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程
     *
     * isolation = Isolation.READ_COMMITTED   事务读已以提交测试
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED)
    public int add3(User user){
        int rows=userService.add(user);
        int total = userService.findListByPage(1, 10).getRecords().size();
        log.info("事务2 数据库总条数：[{}]",total);
        return rows;
    }
    /**
     * propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程
     *
     * isolation = Isolation.REPEATABLE_READ   事务可重复读测试
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ)
    public int add4(User user){
        int rows=userService.add(user);
        int total = userService.findListByPage(1, 10).getRecords().size();
        log.info("事务2 数据库总条数：[{}]",total);
        return rows;
    }
    /**
     * propagation = Propagation.REQUIRES_NEW  线程传播行为  与上层事务线程无关，重新创建一个线程
     *
     * isolation = Isolation.SERIALIZABLE   事务串行化测试
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public int add5(User user){
        int rows=userService.add(user);
        int total = userService.findListByPage(1, 10).getRecords().size();
        log.info("事务2 数据库总条数：[{}]",total);
        return rows;
    }


    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public int delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }

    @ApiOperation(value = "更新")
    @PutMapping()
    public int update(@RequestBody User user){
        return userService.updateData(user);
    }


}
