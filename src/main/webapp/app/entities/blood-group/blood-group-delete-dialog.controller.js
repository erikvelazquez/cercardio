(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('BloodGroupDeleteController',BloodGroupDeleteController);

    BloodGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'BloodGroup'];

    function BloodGroupDeleteController($uibModalInstance, entity, BloodGroup) {
        var vm = this;

        vm.bloodGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BloodGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
