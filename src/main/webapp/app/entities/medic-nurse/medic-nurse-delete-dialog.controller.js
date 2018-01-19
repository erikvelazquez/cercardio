(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicNurseDeleteController',MedicNurseDeleteController);

    MedicNurseDeleteController.$inject = ['$uibModalInstance', 'entity', 'MedicNurse'];

    function MedicNurseDeleteController($uibModalInstance, entity, MedicNurse) {
        var vm = this;

        vm.medicNurse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MedicNurse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
