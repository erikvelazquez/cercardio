(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PrivateHealthInsuranceDeleteController',PrivateHealthInsuranceDeleteController);

    PrivateHealthInsuranceDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrivateHealthInsurance'];

    function PrivateHealthInsuranceDeleteController($uibModalInstance, entity, PrivateHealthInsurance) {
        var vm = this;

        vm.privateHealthInsurance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrivateHealthInsurance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
