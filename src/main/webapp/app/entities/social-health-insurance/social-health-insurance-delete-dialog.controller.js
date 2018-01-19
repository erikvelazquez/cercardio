(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocialHealthInsuranceDeleteController',SocialHealthInsuranceDeleteController);

    SocialHealthInsuranceDeleteController.$inject = ['$uibModalInstance', 'entity', 'SocialHealthInsurance'];

    function SocialHealthInsuranceDeleteController($uibModalInstance, entity, SocialHealthInsurance) {
        var vm = this;

        vm.socialHealthInsurance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SocialHealthInsurance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
