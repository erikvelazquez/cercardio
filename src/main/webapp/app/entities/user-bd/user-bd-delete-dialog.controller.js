(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('UserBDDeleteController',UserBDDeleteController);

    UserBDDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserBD'];

    function UserBDDeleteController($uibModalInstance, entity, UserBD) {
        var vm = this;

        vm.userBD = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserBD.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
