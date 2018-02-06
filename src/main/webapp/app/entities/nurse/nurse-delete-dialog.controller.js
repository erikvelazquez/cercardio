(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('NurseDeleteController',NurseDeleteController);

    NurseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Nurse'];

    function NurseDeleteController($uibModalInstance, entity, Nurse) {
        var vm = this;

        vm.nurse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Nurse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
